package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class LimitClause(private val size: Long) : ClauseExpressBuilder() {

    private val limit = "\nlimit "

    override fun generate(context: Context) {
        super.generate(context)
        if (size > 0) {
            expressBuilder.append(limit)
            expressBuilder.append(size)
        }
    }

}