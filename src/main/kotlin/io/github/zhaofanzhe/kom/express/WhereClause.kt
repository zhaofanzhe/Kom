package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class WhereClause(private val express: LogicExpress<Boolean>) : ClauseExpressBuilder() {

    private val where = "\nwhere "

    override fun generate(context: Context) {
        super.generate(context)
        express.generate(context)
        expressBuilder.append(where)
        expressBuilder.append(express.express())
        paramsBuilder.addAll(express.params())
    }

}