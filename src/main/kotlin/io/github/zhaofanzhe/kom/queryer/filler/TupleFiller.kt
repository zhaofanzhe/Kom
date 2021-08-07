@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Tuple

class TupleFiller(private val instance: Tuple) : Filler<Tuple> {

    override fun set(column: Column<*>, value: Any) {
        instance[column] = value
    }

    override fun getInstance(): Tuple {
        return instance
    }

}