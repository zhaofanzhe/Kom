package com.github.zhaofanzhe.kom.expression

interface QuerySource {

    fun asExpression(): QuerySourceExpression

}