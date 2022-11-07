package com.github.zhaofanzhe.kom.express

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.flavor.Flavor
import com.github.zhaofanzhe.kom.tool.DeclareAliasGenerator
import com.github.zhaofanzhe.kom.tool.TableAliasGenerator

@Suppress("UNCHECKED_CAST")
class Context(
    val flavor: Flavor
) {

    private val tableAliasGenerator = TableAliasGenerator()

    private val declareAliasGenerator = DeclareAliasGenerator(tableAliasGenerator)

    internal var runtime: Runtime? = null

    /**
     * 当前环境表别名
     */
    fun <T : Any> currentTable(table: ITable<T>): ITable<T> {
        if (runtime == null) throw KomException("runtime not fund.")
        return runtime!!.tableRuntime[table] as? ITable<T> ?: throw KomException("not fund currentTable.")
    }

    /**
     * 当前环境表名
     */
    fun <T : Any> currentTableName(table: ITable<T>): String {
        return flavor.name(table.tableName)
    }

    /**
     * 当前环境表别名
     */
    fun <T : Any> currentTableAlias(table: ITable<T>): String {
        return tableAliasGenerator.next(currentTable(table))
    }

    /**
     * 当前环境表列
     */
    fun <T : Any?> currentDeclare(declare: Declare<T>): Declare<T> {
        if (runtime == null) throw KomException("runtime not fund.")
        return runtime!!.declareRuntime[declare] as? Declare<T> ?: declare
    }

    /**
     * 当前环境列名
     */
    fun <T : Any?> currentDeclareName(declare: Declare<T>): String {
        val current = currentDeclare(declare)
        val ref = current.ref
        if (ref != null) {
            return declareAliasGenerator.generate(ref)
        }
        return flavor.name(current.name)
    }

    /**
     * 当前环境列别名
     */
    fun <T : Any?> currentDeclareAlias(declare: Declare<T>): String {
        val current = currentDeclare(declare)
        return declareAliasGenerator.generate(current)
    }

}