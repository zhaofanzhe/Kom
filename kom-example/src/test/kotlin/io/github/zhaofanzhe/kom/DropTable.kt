package io.github.zhaofanzhe.kom

fun main() {
    val database = getDatabase()

    val clause = database.dropTable(Users())

    println(clause)

    clause.execute()
}