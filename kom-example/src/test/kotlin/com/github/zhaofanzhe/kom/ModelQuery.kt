package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.toolkit.eq

fun main() {

    val database = getDatabase()

    val user = database.fetchOne(Users()) { it.id eq 2 }

    println(user)

    val list = database.fetchAll(Users()) { it.username eq "张三" }

    println(list)

}