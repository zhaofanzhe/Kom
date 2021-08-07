package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.LogicExpress
import io.github.zhaofanzhe.kom.express.logic.AndGroup

/**
 * select * from [id = ? and username = ?]
 */
fun and(init: AndGroup.() -> Unit): LogicExpress {
    val scope = AndGroup()
    scope.init()
    return scope
}