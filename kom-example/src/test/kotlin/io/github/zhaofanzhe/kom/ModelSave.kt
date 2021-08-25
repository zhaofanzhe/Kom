package io.github.zhaofanzhe.kom

fun main() {

    val database = getDatabase()

    var user = User(id = 5, username = "王六")

    database.save(user)

    user = User(username = "李四")

    // if no have primaryKey,then create.
    database.save(user)


}