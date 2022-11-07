package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.logic.CompareLogicExpress

/**
 * select * from user where [id <= id]
 */
infix fun <T : Number> Column<*, T>.loe(other: Column<*, T>): CompareLogicExpress {
    return CompareLogicExpress("<=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [id <= ?]
 */
infix fun <T : Number> Column<*, T>.loe(other: T): CompareLogicExpress {
    return CompareLogicExpress("<=", unwrapColumn(this), unwrapColumn(other))
}

/**
 * select * from user where [? <= id]
 */
infix fun <T : Number> T.loe(other: Column<*, T>): CompareLogicExpress {
    return CompareLogicExpress("<=", unwrapColumn(this), unwrapColumn(other))
}
