package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.express.Table

class TableFiller<T : Any>(private val table: Table<T>, instance: Any) : Filler<T> {

    private val filler = AnyFiller(instance)

    override fun set(key: String, value: Any) {
        val column = table.columns().firstOrNull { it.columnName() == key }
        column?.let {
            filler.set(column.fieldName(), value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getInstance(): T {
        return filler.getInstance() as T
    }

}