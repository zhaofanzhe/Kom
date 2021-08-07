package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class SelectClause(private vararg val declares: DeclareExpress) : ClauseExpressBuilder() {

    private val select = "select "

    override fun generate() {
        super.generate()
        expressBuilder.append(select)
        if (declares.isEmpty()) {
            expressBuilder.append("*")
        } else {
            val merge = ExpressMerge(*declares,separator = ", ")
            merge.generate()
            expressBuilder.append(merge.express())
            paramsBuilder.addAll(merge.params())
        }
    }

}