package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column

fun unwrapColumn(any: Any): Any {
    if (any is Column<*>) {
        return any.columnExpress()
    }
    return any
}