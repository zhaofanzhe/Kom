package com.github.zhaofanzhe.kom.utils

import kotlin.reflect.KCallable
import kotlin.reflect.jvm.isAccessible

fun <T : KCallable<*>, R> T.withAccessible(block: (T) -> R): R {
    this.isAccessible = true
    val result = block(this)
    this.isAccessible = false
    return result
}