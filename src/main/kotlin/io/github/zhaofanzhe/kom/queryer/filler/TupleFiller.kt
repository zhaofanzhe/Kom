@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.express.Tuple

class TupleFiller(private val instance: Tuple) : Filler<Tuple> {

    override fun set(key: String, value: Any) {
        instance[key] = value
    }

    override fun getInstance(): Tuple {
        return instance
    }

}