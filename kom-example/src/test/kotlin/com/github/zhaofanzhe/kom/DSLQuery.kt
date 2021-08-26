package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.toolkit.and
import com.github.zhaofanzhe.kom.toolkit.eq

fun main() {

    val database = getDatabase()

    val users = Users()
    val address = Addresses()

    val t1 = database.selectFrom(users)
        .where(and {
            and(users.id eq 1)
        }).subQuery()

    val express = database.select(users, address)
        .from(address)
        .leftJoin(t1)
        .on(and {
            and(users.id eq address.userId)
        })

    println(express)

    val list = express.fetchAll()

    list.forEach {
        println(it[users])
        println(it[address])
    }

    println(express.count())

}