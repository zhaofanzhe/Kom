package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.LogicExpress
import com.github.zhaofanzhe.kom.express.logic.AndGroup

/**
 * select * from [id = ? and username = ?]
 */
fun and(init: AndGroup.() -> Unit): LogicExpress<Boolean> {
    val scope = AndGroup()
    scope.init()
    return scope
}