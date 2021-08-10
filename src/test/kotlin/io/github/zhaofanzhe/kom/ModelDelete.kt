package io.github.zhaofanzhe.kom

fun main() {

    val database = getDatabase()

    val user = User(id = 30)

    database.delete(user)

}