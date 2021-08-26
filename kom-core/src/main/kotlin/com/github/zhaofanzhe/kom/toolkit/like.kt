package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where username like ?
 */
infix fun <T : Any?> Column<*,T>.like(other: T): CompareLogicExpress {
    return CompareLogicExpress("like", unwrapColumn(this), unwrapColumn(other))
}
