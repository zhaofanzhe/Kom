package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where [id >= id]
 */
infix fun <T : Number> Column<*,T>.goe(other: Column<*,T>): CompareLogicExpress {
    return CompareLogicExpress(">=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id >= ?]
 */
infix fun <T : Number> Column<*,T>.goe(other: T): CompareLogicExpress {
    return CompareLogicExpress(">=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? >= id]
 */
infix fun <T : Number> T.goe(other: Column<*,T>): CompareLogicExpress {
    return CompareLogicExpress(">=", unwrapColumn(this), unwrapColumn(other))
}