package io.github.zhaofanzhe.kom.express

data class Column<T>(
    private val table: Table<*>,
    private val columnName: String,
    private val fieldName: String,
) {

    internal fun columnName(): String {
        return columnName
    }

    internal fun fieldName(): String {
        return fieldName
    }

    internal fun table(): Table<*> {
        return table
    }

    internal fun columnExpress(): ColumnExpress {
        return ColumnExpress(this)
    }

    override fun toString(): String {
        return """${table.tableName()}.${columnName}"""
    }

}