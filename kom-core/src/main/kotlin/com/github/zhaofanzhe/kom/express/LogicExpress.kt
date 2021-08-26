package com.github.zhaofanzhe.kom.express


abstract class LogicExpress<T> : Express() {

    internal var logicLayer = 0

    internal abstract fun hasLogic(): Boolean

}