@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.express.Tuple
import io.github.zhaofanzhe.kom.express.declare.Declare

class TupleFiller(private val instance: Tuple) : Filler<Tuple> {

    override fun set(declare: Declare<*>, value: Any?) {
        instance[declare] = value
    }

    override fun getInstance(): Tuple {
        return instance
    }

}