package io.github.zhaofanzhe.kom.support.mysql

import io.github.zhaofanzhe.kom.Users
import io.github.zhaofanzhe.kom.getMysqlDatabase
import io.github.zhaofanzhe.kom.getPostgreDatabase

fun main() {
    val database = getMysqlDatabase()

    val clause = database.dropTable(Users())

    println(clause)

    clause.execute()
}