package io.github.zhaofanzhe.kom.express

data class Column<T>(
    private val columnName: String,
    private val fieldName: String,
) : Express() {

    internal fun columnName(): String {
        return columnName
    }

    internal fun fieldName(): String {
        return fieldName
    }

    override fun generate() {
        // Nothing
    }

    override fun express(): String {
        return columnName()
    }

    override fun params(): Array<Any> {
        return emptyArray()
    }

}