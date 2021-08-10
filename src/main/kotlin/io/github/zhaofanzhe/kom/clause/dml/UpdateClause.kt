package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.Queryer

@Suppress("DuplicatedCode")
class UpdateClause<T : Any>(
    private val queryer: Queryer,
    private val table: Table<T>,
) : Clause(), JoinClauseLink<UpdateClause<T>> {

    private val joins = mutableListOf<JoinClause<UpdateClause<T>>>()

    private val joinTables = mutableListOf<ITable<*>>()

    private val updates = mutableMapOf<Column<*, *>, Any?>()

    private var where: WhereClause? = null

    override fun join(clause: JoinClause<UpdateClause<T>>, table: ITable<*>): UpdateClause<T> {
        joins.add(clause)
        joinTables.add(table)
        return this
    }

    fun innerJoin(table: ITable<*>): JoinClause<UpdateClause<T>> {
        return JoinClause(JoinClause.Genre.INNER, table, this)
    }

    fun leftJoin(table: ITable<*>): JoinClause<UpdateClause<T>> {
        return JoinClause(JoinClause.Genre.LEFT, table, this)
    }

    fun rightJoin(table: ITable<*>): JoinClause<UpdateClause<T>> {
        return JoinClause(JoinClause.Genre.RIGHT, table, this)
    }

    fun fullJoin(table: ITable<*>): JoinClause<UpdateClause<T>> {
        return JoinClause(JoinClause.Genre.FULL, table, this)
    }

    fun set(column: Column<*, *>, value: Any?): UpdateClause<T> {
        updates[column] = value
        return this
    }

    fun where(clause: LogicExpress<Boolean>): UpdateClause<T> {
        this.where = WhereClause(clause)
        return this
    }

    fun execute(): Int {
        if (updates.isEmpty()) throw KomException("no call set().")
        val result = ExpressResult()
        generate(Context(), result)
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

        result += "update "
        result += context.currentTableName(table)
        result += " as "
        result += context.currentTableAlias(table)

        if (joins.isNotEmpty()) {
            joins.forEach { it.generate(context,result) }
        }

        result += "\n set "

        updates.keys.forEachIndexed { index, column ->
            if (index > 0) {
                result += ", "
            }
            result += context.currentTableAlias(column.table)
            result += "."
            result += context.currentDeclareName(column)
            result += " = "
            result.append("?", updates[column])
        }

        where?.generate(context,result)

        if (runtime != null) {
            context.runtime = runtime
        }
    }

}