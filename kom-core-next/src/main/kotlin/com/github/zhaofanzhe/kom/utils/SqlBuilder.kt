package com.github.zhaofanzhe.kom.utils

class SqlBuilder {

    private val _sql = StringBuilder()

    val sql: String
        get() = _sql.toString()

    private val _parameters = mutableListOf<Any?>()

    val parameters: List<Any?>
        get() = _parameters

    fun writeKeyword(word: String) {
        this._sql.append(word)
    }

    fun writeParameters(vararg parameters: Any?) {
        this._parameters.addAll(parameters)
    }

}