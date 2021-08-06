package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.SortExpress
import io.github.zhaofanzhe.kom.express.logic.EqualsLogicExpress
import io.github.zhaofanzhe.kom.express.logic.NotEqualsLogicExpress


/**
 * select * from user where [id = ?]
 */
fun <T> Column<T>.eq(other: T): EqualsLogicExpress {
    return EqualsLogicExpress(this, other as Any)
}

/**
 * select * from user where [id != ?]
 */
fun <T> Column<T>.ne(other: T): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(this, other as Any)
}

/**
 * select * from user order by [id asc]
 */
fun <T> Column<T>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}

/**
 * select * from user order by [id desc]
 */
fun <T> Column<T>.desc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.DESC)
}