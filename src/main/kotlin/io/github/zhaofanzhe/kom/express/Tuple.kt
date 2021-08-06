package io.github.zhaofanzhe.kom.express

class Tuple {

    private val map = HashMap<String, Any>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(field: Field<T>): T? {
        return this[field.columnName()] as T?
    }

    operator fun get(key: String): Any? {
        return map[key]
    }

    operator fun set(key: String, value: Any) {
        map[key] = value
    }

    override fun toString(): String {
        return map.toString()
    }

}