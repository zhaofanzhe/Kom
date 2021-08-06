package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class FromClause(entity: Entity<*>):ClauseExpressBuilder() {

    private var from  = "\nfrom "

    init {
        expressBuilder.append(from)
        expressBuilder.append(entity.tableName())
    }

}