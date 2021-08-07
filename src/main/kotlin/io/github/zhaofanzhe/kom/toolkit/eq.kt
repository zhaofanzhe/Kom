package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.EqualsLogicExpress

/**
 * select * from user where [id = ?]
 */
infix fun <T : Any> Column<T>.eq(other: T): EqualsLogicExpress {
    return EqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? = id]
 */
infix fun <T : Any> T.eq(other: Column<T>): EqualsLogicExpress {
    return EqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id = id]
 * select * from user where [? = ?]
 */
infix fun <T : Any> T.eq(other: T): EqualsLogicExpress {
    return EqualsLogicExpress(unwrapColumn(this), unwrapColumn(other))
}