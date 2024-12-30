package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column

data class OrderByExpression(
    val columns: Column<*>,
) : SqlExpression()