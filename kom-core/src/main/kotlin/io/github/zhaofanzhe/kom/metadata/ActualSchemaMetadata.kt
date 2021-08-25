package io.github.zhaofanzhe.kom.metadata

import io.github.zhaofanzhe.kom.connection.ConnectionFactory

/**
 * 实际库元数据
 */
class ActualSchemaMetadata(
    factory: ConnectionFactory
) : ISchemaMetadata {

    override val schemaName: String

    private val tables = mutableMapOf<String, ActualTableMetadata>()

    val databaseProductName:String

    val databaseProductVersion:String

    init {
        val connection = factory.getConnection()
        this.schemaName = connection.catalog
        this.databaseProductName = connection.metaData.databaseProductName
        this.databaseProductVersion = connection.metaData.databaseProductVersion
        val tables = connection.metaData.getTables(this.schemaName, null, "%", arrayOf("TABLE"))
        while (tables.next()) {
            val tableName = tables.getString(MetadataConstants.TABLE_NAME)
            this.tables[tableName] = ActualTableMetadata(
                connection = connection,
                schemaName = this.schemaName,
                tableName = tableName,
            )
        }
        connection.close()
    }

    override fun tables(): List<ITableMetadata> {
        return this.tables.values.toList()
    }

    override fun table(tableName: String): ITableMetadata? {
        return this.tables[tableName]
    }

}