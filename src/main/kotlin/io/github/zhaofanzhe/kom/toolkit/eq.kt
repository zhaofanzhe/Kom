package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where [id = ?]
 */
infix fun <T : Any> Column<*,T>.eq(other: T): CompareLogicExpress {
    return CompareLogicExpress("=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? = id]
 */
infix fun <T : Any> T.eq(other: Column<*,T>): CompareLogicExpress {
    return CompareLogicExpress("=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id = id]
 * select * from user where [? = ?]
 */
infix fun <T : Any> T.eq(other: T): CompareLogicExpress {
    return CompareLogicExpress("=", unwrapColumn(this), unwrapColumn(other))
}