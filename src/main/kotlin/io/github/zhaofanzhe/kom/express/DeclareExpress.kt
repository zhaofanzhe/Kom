package io.github.zhaofanzhe.kom.express

/**
 * 声明表达式
 */
class DeclareExpress(private val field: Field<*>) : Express() {

    override fun express(): String {
        return field.columnName()
    }

    override fun params(): Array<Any> {
        return emptyArray()
    }

}