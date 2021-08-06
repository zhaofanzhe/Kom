package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class WhereClause(express: LogicExpress) : ClauseExpressBuilder() {

    private val where = "\nwhere "

    init {
        expressBuilder.append(where)
        expressBuilder.append(express.express())
        paramsBuilder.addAll(express.params())
    }

}