package com.github.zhaofanzhe.kom

fun main() {

    val database = getDatabase()

    val users = Users()

    val result = database.insert(users)
        .set(users.username, "zhang san")
        .execute()

    println(result)

}