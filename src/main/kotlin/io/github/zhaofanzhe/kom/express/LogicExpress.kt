package io.github.zhaofanzhe.kom.express


abstract class LogicExpress<T> : Express() {

    internal var logicLayer = 0

    internal abstract fun hasLogic(): Boolean

}

fun LogicExpress<*>?.notNullAndHasLogic(): Boolean {
    return this != null && this.hasLogic()
}