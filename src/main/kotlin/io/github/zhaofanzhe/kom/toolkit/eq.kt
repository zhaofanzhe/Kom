package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.EqualsLogicExpress

/**
 * select * from user where [id = ?]
 */
fun <T> Column<T>.eq(other: T): EqualsLogicExpress {
    return EqualsLogicExpress(this.columnExpress(), other as Any)
}