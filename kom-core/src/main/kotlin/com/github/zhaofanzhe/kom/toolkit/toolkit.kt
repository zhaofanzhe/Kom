package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.declare.Declare

fun unwrapColumn(any: Any?): Any? {
    if (any is Declare<*>) {
        return any.express()
    }
    return any
}