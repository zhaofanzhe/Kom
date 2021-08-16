package io.github.zhaofanzhe.kom.support.postgresql

import io.github.zhaofanzhe.kom.Users
import io.github.zhaofanzhe.kom.getDatabase

fun main() {
    val database = getDatabase()

    val clause = database.createTable(Users())

    println(clause)

    clause.execute()
}