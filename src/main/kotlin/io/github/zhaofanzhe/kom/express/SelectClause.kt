package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class SelectClause(private vararg val declares: DeclareExpress) : ClauseExpressBuilder() {

    private val select = "select "

    override fun generate(context: Context) {
        super.generate(context)
        expressBuilder.append(select)
        if (declares.isEmpty()) {
            expressBuilder.append("*")
        } else {
            val merge = ExpressMerge(*declares,separator = ", ")
            merge.generate(context)
            expressBuilder.append(merge.express())
            paramsBuilder.addAll(merge.params())
        }
    }

}