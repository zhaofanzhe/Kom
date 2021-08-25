package io.github.zhaofanzhe.kom.clause.ddl

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.clause.ddl.alter.AlterSubClause
import io.github.zhaofanzhe.kom.clause.ddl.alter.AlterTableAddColumn
import io.github.zhaofanzhe.kom.clause.ddl.alter.AlterTableDropColumn
import io.github.zhaofanzhe.kom.clause.ddl.alter.AlterTableModifyColumn
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.flavor.Flavor
import io.github.zhaofanzhe.kom.queryer.Queryer

class AlterTableClause<T : Any>(
    private val queryer: Queryer,
    private val flavor: Flavor,
    private val table: Table<T>,
) : Clause() {

    private var clause: AlterSubClause? = null

    fun drop(column: Column<T, *>): AlterTableClause<T> {
        this.clause = AlterTableDropColumn(flavor, column)
        return this
    }

    fun add(column: Column<T, *>): AlterTableClause<T> {
        this.clause = AlterTableAddColumn(flavor, column)
        return this
    }

    fun modify(column: Column<T, *>): AlterTableClause<T> {
        this.clause = AlterTableModifyColumn(flavor, column)
        return this
    }

    override fun generate(context: Context, result: ExpressResult) {
        result += "alter table "
        result += flavor.name(table.tableName)
        result += " "
        clause?.generate(context, result)
    }

    fun execute(): Int {
        val result = ExpressResult()
        generate(Context(flavor), result)
        return queryer.execute(result.express(), result.params())
    }

    override fun toString(): String {
        val context = Context(flavor)
        val result = ExpressResult()
        generate(context, result)
        return result.express()
    }

}