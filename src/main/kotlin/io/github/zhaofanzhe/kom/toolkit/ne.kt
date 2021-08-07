package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.NotEqualsLogicExpress

/**
 * select * from user where [id != ?]
 */
infix fun <T : Any> Column<T>.ne(other: T): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? != id]
 */
infix fun <T : Any> T.ne(other: Column<T>): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id != id]
 * select * from user where [? != ?]
 */
infix fun <T : Any> T.ne(other: T): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}

