@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

class MutableMapFiller(private val instance: MutableMap<String, Any>) : Filler<MutableMap<String, Any>> {

    override fun set(key: String, value: Any) {
        instance[key] = value
    }

    override fun getInstance(): MutableMap<String, Any> {
        return instance
    }

}