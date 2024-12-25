package com.github.zhaofanzhe.kom.table

abstract class Table(val tableName: String) {
    protected fun <T> column(columnName: String) = Column<T>(columnName)
}