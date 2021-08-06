package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Field
import io.github.zhaofanzhe.kom.express.SortExpress
import io.github.zhaofanzhe.kom.express.logic.EqualsLogicExpress
import io.github.zhaofanzhe.kom.express.logic.NotEqualsLogicExpress


/**
 * select * from user where [id = ?]
 */
fun <T> Field<T>.eq(other: T): EqualsLogicExpress {
    return EqualsLogicExpress(this, other as Any)
}

/**
 * select * from user where [id != ?]
 */
fun <T> Field<T>.neq(other: T): NotEqualsLogicExpress {
    return NotEqualsLogicExpress(this, other as Any)
}

/**
 * select * from user order by [id asc]
 */
fun <T> Field<T>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}

/**
 * select * from user order by [id desc]
 */
fun <T> Field<T>.desc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.DESC)
}