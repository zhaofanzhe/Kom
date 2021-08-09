package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.toolkit.and
import io.github.zhaofanzhe.kom.toolkit.eq

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
        .limit(10, 5)

    println(express)

    val list = express.fetchAll()

    list.forEach {
        println(it[users])
        println(it[address])
    }

    println(express.count())

}