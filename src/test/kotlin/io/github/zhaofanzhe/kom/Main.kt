package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.toolkit.*
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
) {
    constructor() : this(id = 0, username = "")
}

class Users : Table<User>(User::class) {
    val id = column(User::id)
    val username = column(User::username)
}

data class Address(
    var id: Int = 0,
    var address: String = "",
    var userId: Int = 0,
) {
    constructor() : this(id = 0, address = "", userId = 0)
}

class Addresses : Table<Address>(Address::class) {
    var id = column(Address::id)
    var address = column(Address::address)
    var userId = column(Address::userId)
}

fun main() {

    val database = getDatabase()

    val address = Addresses()
    val users = Users()

    val count = count(users.id)

    val express = database.select(users.id,users.username,count)
        .from(users)
        .leftJoin(address)
        .on(and {
            and(users.id eq address.userId)
        })
        .where(and {
            and(users.id gt 1)
        })

    println(express)

    val list = express.fetchAll()

    list.forEach {
        println("""id = ${it[users.id]}, username = ${it[users.username]}, count = ${it[count]}""")
    }

}

fun getDatabase(): Database {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "123456")
    return Database(object : ConnectionFactory {
        override fun getConnection(): Connection {
            return connection
        }
    })
}