package com.github.zhaofanzhe.kom.dsl.statement

import com.github.zhaofanzhe.kom.dsl.Bundle

interface Statement {

    fun generateStatement(): Bundle

}
