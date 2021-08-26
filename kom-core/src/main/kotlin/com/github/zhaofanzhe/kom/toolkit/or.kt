package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.LogicExpress
import com.github.zhaofanzhe.kom.express.logic.OrGroup

/**
 * select * from [id = ? or username = ?]
 */
fun or(init: OrGroup.() -> Unit): LogicExpress<Boolean> {
    val scope = OrGroup()
    scope.init()
    return scope
}