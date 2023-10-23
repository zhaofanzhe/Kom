package com.github.zhaofanzhe.kom.dsl.selectable

import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

interface Selectable {

    fun generateSelectable(): Bundle

    fun flatName(): String? = null

}
