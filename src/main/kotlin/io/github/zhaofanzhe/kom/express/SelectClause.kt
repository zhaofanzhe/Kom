package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class SelectClause(vararg declares: DeclareExpress) : ClauseExpressBuilder() {

    private val select = "select "

    init {
        expressBuilder.append(select)
        if (declares.isEmpty()) {
            expressBuilder.append("*")
        } else {
            val express = ExpressMerge(*declares,separator = ", ")
            expressBuilder.append(express.express())
            paramsBuilder.addAll(express.params())
        }
    }

}