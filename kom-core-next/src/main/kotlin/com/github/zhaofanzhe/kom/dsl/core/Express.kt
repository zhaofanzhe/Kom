package com.github.zhaofanzhe.kom.dsl.core

import com.github.zhaofanzhe.kom.dsl.Bundle

interface Express<R> {

    fun generateExpress(): Bundle

}