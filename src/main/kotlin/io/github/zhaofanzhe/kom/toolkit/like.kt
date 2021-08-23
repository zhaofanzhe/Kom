package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where username like ?
 */
infix fun <T : Any?> Column<*,T>.like(other: T): CompareLogicExpress {
    return CompareLogicExpress("like", unwrapColumn(this), unwrapColumn(other))
}
