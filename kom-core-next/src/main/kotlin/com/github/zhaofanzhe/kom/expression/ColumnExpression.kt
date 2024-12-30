package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column

class ColumnExpression<T>(val column: Column<T>) : ScalarExpression<T>()