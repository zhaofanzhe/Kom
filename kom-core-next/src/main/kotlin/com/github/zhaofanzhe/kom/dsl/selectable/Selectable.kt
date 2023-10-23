package com.github.zhaofanzhe.kom.dsl.selectable

import com.github.zhaofanzhe.kom.dsl.Bundle

interface Selectable {

    fun generateSelectable(): Bundle

    fun flatName(): String? = null

}
