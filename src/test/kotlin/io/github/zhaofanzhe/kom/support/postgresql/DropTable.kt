package io.github.zhaofanzhe.kom.support.postgresql

import io.github.zhaofanzhe.kom.Users
import io.github.zhaofanzhe.kom.getPostgreDatabase

fun main() {
    val database = getPostgreDatabase()

    val clause = database.dropTable(Users())

    println(clause)

    clause.execute()
}