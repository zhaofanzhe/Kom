package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.NotEqualsLogicExpress

/**
 * select * from user where [id != ?]
 */
fun <T> Column<T>.ne(other: T): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(this.columnExpress(), other as Any)
}