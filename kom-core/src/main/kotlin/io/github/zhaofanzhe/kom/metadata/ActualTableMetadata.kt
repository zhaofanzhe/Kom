package io.github.zhaofanzhe.kom.metadata

import java.sql.Connection

/**
 * 实际表元数据
 */
class ActualTableMetadata(
    connection: Connection,
    override val schemaName: String,
    override val tableName: String,
) : ITableMetadata {

    private val columns = mutableMapOf<String, ActualColumnMetadata>()

    init {
        val columns = connection.metaData.getColumns(schemaName, null, tableName, "%")
        while (columns.next()) {
            val columnCount = columns.metaData.columnCount
//            for (i in 1..columnCount) {
//                println(columns.metaData.getColumnName(i))
//                println(columns.getObject(i))
//            }
            val columnName = columns.getString(MetadataConstants.COLUMN_NAME)
            this.columns[columnName] = ActualColumnMetadata(
                connection = connection,
                schemaName = this.schemaName,
                tableName = this.tableName,
                columnName = columnName
            )
        }
    }

    override fun columns(): List<IColumnMetadata> {
        return columns.values.toList()
    }

    override fun column(columnName: String): IColumnMetadata? {
        return columns[columnName]
    }

}