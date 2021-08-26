package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.toolkit.eq

fun main() {

    val database = getDatabase()

    val users = Users()
    val address = Addresses()

    val express = database.update(users)
        .leftJoin(address)
        .on(address.userId eq users.id)
        .set(address.address,"哈哈哈")
        .where(users.id eq 1)

    println(express)

    println(express.execute())

}