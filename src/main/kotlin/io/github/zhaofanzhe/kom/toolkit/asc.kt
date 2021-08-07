package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.SortExpress

/**
 * select * from user order by [id asc]
 */
fun <T> Column<T>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}