package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.Entity
import io.github.zhaofanzhe.kom.toolkit.*
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = ""
)

class Users : Entity<User>(User::class.java) {
    val id = field<Int>("id")
    val username = field<String>("username")
}

fun main() {

    val database = getDatabase()

    val users = Users()

    val express = database.select(users.id, users.username)
        .from(users)
        .where(and {
            and(users.username.neq("张三"))
            or {
                or(users.id.eq(1))
                or(users.id.eq(2))
            }
        })
        .groupBy(users.id)
        .orderBy(users.id.desc())
        .limit(10)
        .offset(10)

    println(express)

    val list = express.fetchAll()

    println(list)

}

fun getDatabase(): Database {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "123456")
    return Database(object : ConnectionFactory {
        override fun getConnection(): Connection {
            return connection
        }
    })
}