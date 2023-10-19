package com.github.zhaofanzhe.kom.dsl.core

import com.github.zhaofanzhe.kom.dsl.Bundle

interface Statement {

    fun generateStatement(): Bundle

}
