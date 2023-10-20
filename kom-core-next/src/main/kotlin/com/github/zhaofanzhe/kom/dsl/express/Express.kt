package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

interface Express<R> {

    fun generateExpress(): Bundle

}