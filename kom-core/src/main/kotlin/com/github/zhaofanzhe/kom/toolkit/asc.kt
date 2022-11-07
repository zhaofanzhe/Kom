package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.clause.dml.SortExpress
import com.github.zhaofanzhe.kom.express.Column

/**
 * select * from user order by [id asc]
 */
fun Column<*, *>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}