package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class FromClause(private val table: Table<*>):ClauseExpressBuilder() {

    private var from  = "\nfrom "

    override fun generate() {
        super.generate()
        expressBuilder.append(from)
        expressBuilder.append(table.tableName())
    }

}