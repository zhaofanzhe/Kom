package com.github.zhaofanzhe.kom.metadata

import java.sql.Connection

class ActualColumnMetadata(
    connection: Connection,
    override val schemaName: String,
    override val tableName: String,
    override val columnName: String,
) : IColumnMetadata {

    init {

    }

}