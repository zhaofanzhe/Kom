package io.github.zhaofanzhe.kom.express

/**
 * 声明表达式
 */
class DeclareExpress(private val column: Column<*>) : Express() {

    override fun express(): String {
        return column.columnName()
    }

    override fun params(): Array<Any> {
        return emptyArray()
    }

}