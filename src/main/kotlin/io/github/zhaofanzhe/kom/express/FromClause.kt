package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class FromClause(table: Table<*>):ClauseExpressBuilder() {

    private var from  = "\nfrom "

    init {
        expressBuilder.append(from)
        expressBuilder.append(table.tableName())
    }

}