package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.clause.dml.SortExpress
import com.github.zhaofanzhe.kom.express.Column

/**
 * select * from user order by [id desc]
 */
fun Column<*, *>.desc(): SortExpress {
    return SortExpress(this, SortExpress.Genre.DESC)
}