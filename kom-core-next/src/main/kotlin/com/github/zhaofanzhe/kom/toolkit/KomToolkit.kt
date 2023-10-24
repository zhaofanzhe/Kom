package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.alterTable
import com.github.zhaofanzhe.kom.connection.execute
import com.github.zhaofanzhe.kom.createTable
import com.github.zhaofanzhe.kom.dsl.statement.ddl.addColumn
import com.github.zhaofanzhe.kom.dsl.statement.ddl.execute
import com.github.zhaofanzhe.kom.dsl.statement.ddl.modifyColumn
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.table.toStructure
import com.github.zhaofanzhe.kom.structure.ColumnStructure
import com.github.zhaofanzhe.kom.structure.TableStructure
import java.sql.Connection
import java.sql.ResultSet

class KomToolkit {

    companion object {}

}

/**
 * 获取表结构
 */
fun KomToolkit.Companion.getColumnType(resultSet: ResultSet): String {
    // 类型未补全
    val typeName = resultSet.getString("TYPE_NAME").lowercase()
    val columnSize = resultSet.getString("COLUMN_SIZE")
    return when (typeName) {
        "varchar" -> "varchar(${columnSize})"
        "char" -> "char(${columnSize})"
        "decimal" -> {
            val decimalDigits = resultSet.getString("DECIMAL_DIGITS")
            "decimal(${columnSize},${decimalDigits})"
        }

        else -> typeName
    }
}

/**
 * 获取表结构
 */
fun KomToolkit.Companion.getTableStructure(connection: Connection, tableName: String): TableStructure? {

    var resultSet = connection.metaData.getTables(connection.catalog, connection.schema, tableName, null)

    if (!resultSet.next()) {
        return null
    }

    resultSet = connection.metaData.getColumns(connection.catalog, connection.schema, tableName, null)

    val columns = mutableListOf<ColumnStructure>()

    while (resultSet.next()) {
        val columnName = resultSet.getString("COLUMN_NAME")
        val isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT") == "YES"
        val isNullable = resultSet.getString("IS_NULLABLE") == "YES"
        val comment = resultSet.getString("REMARKS")
        columns += ColumnStructure(
            name = columnName,
            type = getColumnType(resultSet),
            isAutoIncrement = isAutoIncrement,
            isNullable = isNullable,
            comment = comment,
        )
    }

    // TODO primaryKey indexes uniqueIndexes 暂未实现
    return TableStructure(
        name = tableName,
        columns = columns,
        primaryKey = listOf(),
        indexes = mapOf(),
        uniqueIndexes = mapOf(),
    )
}

/**
 * 迁移表
 * 自动执行: 新建表、新建字段、修改字段、添加索引
 * 不会执行: 删除表、删除字段、删除索引
 * 不保证字段顺序(部分数据库不支持字段顺序), 通常新建字段追加到已存在字段末尾
 */
fun KomToolkit.Companion.migrate(database: Database, table: Table) {
    database.factory.execute { connection ->
        val source = table.toStructure()
        val target = getTableStructure(connection, table.name)
        // 创建表
        if (target == null) {
            database.createTable(table).execute()
            return@execute
        }
        for (sourceColumn in source.columns) {
            val targetColumn = target.columns.find { it.name == sourceColumn.name }
            // 创建列
            if (targetColumn == null) {
                val column = table.columns.find { it.name == sourceColumn.name }!!
                database.alterTable(table).addColumn(column).execute()
                continue
            }
            // 修改列
            if (sourceColumn != targetColumn) {
                val column = table.columns.find { it.name == sourceColumn.name }!!
                database.alterTable(table).modifyColumn(column).execute()
                continue
            }
        }
    }
}