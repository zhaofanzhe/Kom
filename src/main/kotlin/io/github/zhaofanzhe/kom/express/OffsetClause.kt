package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

@Suppress("PrivatePropertyName")
class OffsetClause(private val size: Long) : ClauseExpressBuilder() {

    private val OFFSET = " offset "

    override fun generate(context: Context) {
        super.generate(context)
        if (size > 0) {
            expressBuilder.append(OFFSET)
            expressBuilder.append(size)
        }
    }

}