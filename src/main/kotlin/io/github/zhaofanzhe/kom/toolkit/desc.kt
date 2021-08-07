package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.SortExpress

/**
 * select * from user order by [id desc]
 */
fun <T> Column<T>.desc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.DESC)
}