package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class OffsetClause(size: Long) : ClauseExpressBuilder() {

    private val offset = "offset "

    init {
        if (size > 0) {
            expressBuilder.append(offset)
            expressBuilder.append(size)
        }
    }

}