package com.github.zhaofanzhe.kom.dsl

import com.github.zhaofanzhe.kom.dsl.core.Express
import com.github.zhaofanzhe.kom.dsl.express.ComposeExpress

class Scope(
    val op: String,
) {
    val expresses = mutableListOf<Express<Boolean>>()
}

fun Scope.use(other: Express<Boolean>): Scope {
    this.expresses += other
    return this
}

fun Scope.use(other: Scope) {
    this.expresses += other.express()
}

fun Scope.useAnd(fn: Scope.() -> Unit): Scope {
    return use(and(fn).express())
}

fun Scope.useOr(fn: Scope.() -> Unit): Scope {
    return use(or(fn).express())
}

fun Scope.express(brackets: Boolean = true): Express<Boolean> {
    return ComposeExpress(op, expresses, brackets)
}

fun and(fn: Scope.() -> Unit): Scope {
    return Scope("and").apply { fn() }
}

fun or(fn: Scope.() -> Unit): Scope {
    return Scope("or").apply { fn() }
}

fun useAnd(fn: Scope.() -> Unit): Express<Boolean> {
    return and(fn).express(brackets = false)
}

fun useOr(fn: Scope.() -> Unit): Express<Boolean> {
    return or(fn).express(brackets = false)
}