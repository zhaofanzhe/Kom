package com.github.zhaofanzhe.kom.dsl.function

import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable


class SelectableFunction<R>(
    name: String,
    args: List<Express<*>>
) : Function<R>(name, args), Selectable {

    override fun generateSelectable(): Bundle {
        return generateExpress()
    }

}
