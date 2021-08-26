package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where [id < id]
 */
infix fun <T : Number> Column<*,T>.lt(other: Column<*,T>): CompareLogicExpress {
    return CompareLogicExpress("<", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id < ?]
 */
infix fun <T : Number> Column<*,T>.lt(other: T): CompareLogicExpress {
    return CompareLogicExpress("<", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? < id]
 */
infix fun <T : Number> T.lt(other: Column<*,T>): CompareLogicExpress {
    return CompareLogicExpress("<", unwrapColumn(this), unwrapColumn(other))
}
