package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.clause.dml.SortExpress

/**
 * select * from user order by [id asc]
 */
fun Column<*, *>.asc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.ASC)
}