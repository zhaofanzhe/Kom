package io.github.zhaofanzhe.kom

fun main() {
    val database = getDatabase()

    val clause = database.createTable(Users())

    println(clause)

    clause.execute()
}