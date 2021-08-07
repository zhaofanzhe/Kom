package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.LogicExpress
import io.github.zhaofanzhe.kom.express.logic.OrGroup

/**
 * select * from [id = ? or username = ?]
 */
fun or(init: OrGroup.() -> Unit): LogicExpress {
    val scope = OrGroup()
    scope.init()
    return scope
}