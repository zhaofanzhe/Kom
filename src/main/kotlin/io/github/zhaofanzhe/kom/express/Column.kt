package io.github.zhaofanzhe.kom.express

data class Column<T>(
    private val field: String,
) : Express() {

    internal fun fieldName(): String {
        return field
    }

    internal fun columnName(): String {
        return field
    }

    override fun express(): String {
        return columnName()
    }

    override fun params(): Array<Any> {
        return emptyArray()
    }

}