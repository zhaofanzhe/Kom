package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.entity.Entity
import io.github.zhaofanzhe.kom.express.Table
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
) : Entity<Users>(Users::class) {
    constructor() : this(id = 0, username = "")
}

class Users : Table<User>(User::class) {
    val id = column(User::id, primaryKey = true)
    val username = column(User::username)
}

data class Address(
    var id: Int = 0,
    var address: String = "",
    var userId: Int = 0,
) : Entity<Addresses>(Addresses::class) {
    constructor() : this(id = 0, address = "", userId = 0)
}

class Addresses : Table<Address>(Address::class) {
    var id = column(Address::id, primaryKey = true)
    var address = column(Address::address)
    var userId = column(Address::userId)
}

fun getDatabase(): Database {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "123456")
    return Database(object : ConnectionFactory {
        override fun getConnection(): Connection {
            return connection
        }
    })
}