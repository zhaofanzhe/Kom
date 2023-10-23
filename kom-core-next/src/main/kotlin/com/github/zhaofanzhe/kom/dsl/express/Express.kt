package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle

interface Express<R> {

    fun generateExpress(): Bundle

}