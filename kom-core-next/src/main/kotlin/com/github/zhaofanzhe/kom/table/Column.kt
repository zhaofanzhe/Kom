package com.github.zhaofanzhe.kom.table

interface ColumnDeclaring {

//    public fun asExpression(): ScalarExpression<T>

}

class Column<T>(val columnName: String) {
    override fun toString() = columnName
}