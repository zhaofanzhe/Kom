package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.core.Express

class SuffixExpress(
    val express: Express<*>,
    val suffix: String,
) : Express<Boolean> {

    override fun generateExpress(): Bundle {
        val bundle = express.generateExpress()
        return Bundle(
            sql = "${bundle.sql} $suffix",
        )
    }

}

fun <R> Express<R>.isNull(): Express<Boolean> {
    return SuffixExpress(this, "is null")
}

fun <R> Express<R>.isNotNull(): Express<Boolean> {
    return SuffixExpress(this, "is not null")
}