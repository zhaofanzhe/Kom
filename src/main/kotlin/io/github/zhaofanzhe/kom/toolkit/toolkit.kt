package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.declare.Declare

fun unwrapColumn(any: Any?): Any? {
    if (any is Declare<*>) {
        return any.express()
    }
    return any
}