package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class OffsetClause(private val size: Long) : ClauseExpressBuilder() {

    private val offset = " offset "

    override fun generate(context: Context) {
        super.generate(context)
        if (size > 0) {
            expressBuilder.append(offset)
            expressBuilder.append(size)
        }
    }

}