package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.flavor.Flavor
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.Queryer

@Suppress("DuplicatedCode")
class DeleteClause<T : Any>(
    private val queryer: Queryer,
    private val flavor: Flavor,
    private val table: Table<T>,
) : Clause(), JoinClauseLink<DeleteClause<T>> {

    private var where: WhereClause? = null

    private var joins: MutableList<JoinClause<DeleteClause<T>>> = mutableListOf()

    private var joinTables: MutableList<ITable<*>> = mutableListOf()

    fun where(clause: LogicExpress<Boolean>): DeleteClause<T> {
        this.where = WhereClause(clause)
        return this
    }

    override fun join(clause: JoinClause<DeleteClause<T>>, table: ITable<*>): DeleteClause<T> {
        joins.add(clause)
        joinTables.add(table)
        return this
    }

    fun innerJoin(table: ITable<*>): JoinClause<DeleteClause<T>> {
        return JoinClause(JoinClause.Genre.INNER, table, this)
    }

    fun leftJoin(table: ITable<*>): JoinClause<DeleteClause<T>> {
        return JoinClause(JoinClause.Genre.LEFT, table, this)
    }

    fun rightJoin(table: ITable<*>): JoinClause<DeleteClause<T>> {
        return JoinClause(JoinClause.Genre.RIGHT, table, this)
    }

    fun fullJoin(table: ITable<*>): JoinClause<DeleteClause<T>> {
        return JoinClause(JoinClause.Genre.FULL, table, this)
    }

    fun execute(): Int {
        val result = ExpressResult()
        generate(Context(flavor), result)
        return queryer.execute(result.express(), result.params())
    }

    override fun generate(context: Context, result: ExpressResult) {

        val runtime = context.runtime

        val tables = mutableListOf<ITable<*>>()
        val declares = mutableListOf<Declare<*>>()

        tables.add(table)
        tables.addAll(joinTables)
        declares.addAll(table.declares())
        joinTables.forEach { declares.addAll(it.declares()) }

        context.runtime = Runtime(tables, declares)

        result += "delete "
        result += context.currentTableAlias(table)
        result += " from "
        result += context.currentTableName(table)
        result += " as "
        result += context.currentTableAlias(table)

        joins.forEach { it.generate(context, result) }

        where?.generate(context, result)

        if (runtime != null) {
            context.runtime = runtime
        }
    }

}