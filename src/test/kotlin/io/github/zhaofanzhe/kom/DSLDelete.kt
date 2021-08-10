package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.toolkit.and
import io.github.zhaofanzhe.kom.toolkit.eq

fun main() {

    val database = getDatabase()

    val users = Users()

    val address = Addresses()

    val express = database.delete(users)
        .leftJoin(address)
        .on(and {
            and(users.id eq address.userId)
        })
        .where(and {
            and(address.address eq "哈哈哈")
        })

    println(express)

    println(express.execute())

}