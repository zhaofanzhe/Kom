package io.github.zhaofanzhe.kom.clause.ddl

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.flavor.Flavor
import io.github.zhaofanzhe.kom.queryer.Queryer

@Suppress("UNCHECKED_CAST")
class CreateTableClause<T : Any>(
    private val queryer: Queryer,
    private val flavor: Flavor,
    private val table: Table<T>,
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        val flavor = context.flavor
        result += "create table "
        result += flavor.name(table.tableName)
        val columns = table.declares().map { it as Column<T, *> }
        result += "("
        columns.forEachIndexed { index, column ->
            if (index > 0) {
                result += ", "
            }
            result += "\n\t"
            result += flavor.name(column.name)
            result += " "
            result += flavor.typedef(column) ?: throw KomException("Unable to resolve class ${column.clazz}.")
        }
        val primaryKeys = table.primaryKeys()
        if (primaryKeys.isNotEmpty()) {
            result += ", "
            result += "\n\t"
            result += "constraint "
            result += """${table.tableName}_pk"""
            result += " primary key ("
            primaryKeys.forEachIndexed { index, column ->
                if (index > 0) {
                    result += ", "
                }
                result += flavor.name(column.name)
            }
            result += ")"
        }

        result += "\n)"
    }

    fun execute(): Int {
        val result = ExpressResult()
        generate(Context(flavor), result)
        println(result.express())
//        return queryer.execute(result.express(), result.params())
        return 0;
    }

    override fun toString(): String {
        val context = Context(flavor)
        val result = ExpressResult()
        generate(context, result)
        return result.express()
    }

}