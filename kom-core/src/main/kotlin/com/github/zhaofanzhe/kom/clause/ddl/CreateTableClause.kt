package com.github.zhaofanzhe.kom.clause.ddl

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.Table
import com.github.zhaofanzhe.kom.flavor.Flavor
import com.github.zhaofanzhe.kom.queryer.Queryer

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
        val columns = table.declares() as List<Column<T, *>>
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
        // 主键约束
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

        // 默认 unique 约束
        columns.filter { it.unique && it.uniqueKey == null }.forEach {
            result += ", "
            result += "\n\t"
            result += "constraint "
            result += """${table.tableName}_unique_${it.name}"""
            result += " unique ("
            result += flavor.name(it.name)
            result += ")"
        }

        // 自定义 unique 约束
        columns.filter { it.unique && it.uniqueKey != null && it.uniqueKey != "" }
            .map { it.uniqueKey }.distinct()
            .filterNotNull()
            .forEach { uniqueKey ->
                val list = columns.filter { it.uniqueKey == uniqueKey }
                result += ", "
                result += "\n\t"
                result += "constraint "
                result += """${table.tableName}_unique_${uniqueKey}"""
                result += " unique ("
                list.forEachIndexed { index, column ->
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
        return queryer.execute(result.express(), result.params())
    }

    override fun toString(): String {
        val context = Context(flavor)
        val result = ExpressResult()
        generate(context, result)
        return result.express()
    }

}