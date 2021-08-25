package io.github.zhaofanzhe.kom

fun main() {

    val database = getDatabase()

    val user = User(username = "张三")

    database.create(user)

}