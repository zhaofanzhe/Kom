package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class LimitClause(size: Long) : ClauseExpressBuilder() {

    private val limit = "\nlimit "

    init {
        if (size > 0) {
            expressBuilder.append(limit)
            expressBuilder.append(size)
        }
    }

}