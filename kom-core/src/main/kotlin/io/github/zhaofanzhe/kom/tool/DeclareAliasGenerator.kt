package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.ITable
import io.github.zhaofanzhe.kom.express.declare.Declare

@Suppress("MemberVisibilityCanBePrivate")
class DeclareAliasGenerator(private val tableAliasGenerator: TableAliasGenerator) {

    internal val columns = mutableMapOf<ITable<*>, MutableMap<Declare<*>, String>>()

    internal val others = mutableMapOf<Declare<*>, String>()

    internal val othersGenerator = AliasGenerator()

    fun generate(column: Column<*,*>): String {
        val table = column.table
        return columns.getOrPut(table) {
            mutableMapOf()
        }.getOrPut(column) {
            """${tableAliasGenerator.next(table)}__${column.name}"""
        }
    }

    fun generate(declare: Declare<*>): String {
        if (declare is Column<*,*>){
            return generate(declare)
        }
        return others.getOrPut(declare) {
            othersGenerator.generate("__col")
        }
    }

}