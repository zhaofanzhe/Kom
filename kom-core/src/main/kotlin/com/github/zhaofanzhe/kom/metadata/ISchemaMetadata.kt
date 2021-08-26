package com.github.zhaofanzhe.kom.metadata

interface ISchemaMetadata {

    val schemaName: String

    /**
     * 获取所有的表元数据
     */
    fun tables(): List<ITableMetadata>

    /**
     * 获取表的元数据
     */
    fun table(tableName: String): ITableMetadata?

}