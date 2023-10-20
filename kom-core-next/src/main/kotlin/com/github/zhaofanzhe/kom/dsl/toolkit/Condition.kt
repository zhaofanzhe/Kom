package com.github.zhaofanzhe.kom.dsl.toolkit

import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.express.ConditionExpress

class Condition(
    val op: String,
) {
    val expresses = mutableListOf<Express<Boolean>>()
}


fun Condition.use(other: Express<Boolean>): Condition {
    this.expresses += other
    return this
}

fun Condition.use(other: Condition) {
    this.expresses += other.express()
}

fun Condition.useAnd(fn: Condition.() -> Unit): Condition {
    return use(newAndCondition(fn).express())
}

fun Condition.useOr(fn: Condition.() -> Unit): Condition {
    return use(newOrCondition(fn).express())
}

fun Condition.express(brackets: Boolean = true): Express<Boolean> {
    return ConditionExpress(op, expresses, brackets)
}

fun useAnd(fn: Condition.() -> Unit): Express<Boolean> {
    return newAndCondition(fn).express(brackets = false)
}

fun useOr(fn: Condition.() -> Unit): Express<Boolean> {
    return newOrCondition(fn).express(brackets = false)
}

private fun newAndCondition(fn: Condition.() -> Unit): Condition {
    return Condition("and").apply { fn() }
}

private fun newOrCondition(fn: Condition.() -> Unit): Condition {
    return Condition("or").apply { fn() }
}
