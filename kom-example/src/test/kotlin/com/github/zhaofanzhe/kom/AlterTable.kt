package com.github.zhaofanzhe.kom

fun main() {
    val database = getDatabase()

    val users = Users()

    val clause = database.alterTable(users).modify(users.username)

    println(clause)

    clause.execute()
}