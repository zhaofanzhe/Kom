package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.clause.SortExpress

/**
 * select * from user order by [id asc]
 */
fun Column<*, *>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}