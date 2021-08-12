package io.github.zhaofanzhe.kom.metadata

/**
 * 表元数据
 */
class TableMetadata:ITableMetadata {

    override val schemaName: String = ""

    override val tableName: String = ""

    override fun columns(): List<IColumnMetadata> {
        return emptyList()
    }

    override fun column(columnName: String): IColumnMetadata? {
        return null
    }
}