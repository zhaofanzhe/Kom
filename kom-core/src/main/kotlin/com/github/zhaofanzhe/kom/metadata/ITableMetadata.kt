package com.github.zhaofanzhe.kom.metadata

interface ITableMetadata {

    val schemaName: String

    val tableName: String

    fun columns(): List<IColumnMetadata>

    fun column(columnName: String): IColumnMetadata?

}