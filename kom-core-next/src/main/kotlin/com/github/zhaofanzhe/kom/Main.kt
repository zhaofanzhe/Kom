package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.core.flat
import com.github.zhaofanzhe.kom.dsl.column.autoIncrement
import com.github.zhaofanzhe.kom.dsl.column.primaryKey
import com.github.zhaofanzhe.kom.dsl.column.unique
import com.github.zhaofanzhe.kom.dsl.statement.dml.fetchAll
import com.github.zhaofanzhe.kom.dsl.statement.dml.from
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.table.int
import com.github.zhaofanzhe.kom.dsl.table.varchar
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
) {
    constructor() : this(id = 0, username = "")
}

class Users : Table("users") {
    val id = int("id").primaryKey().autoIncrement()
    val username = varchar("username").unique()
}

class Addresses : Table("addresses") {
    var id = int("id").primaryKey().autoIncrement()
    var address = varchar("address")
    var userId = int("user_id")
}

fun getMySQLConnectionFactory(): ConnectionFactory {
    return object : ConnectionFactory {
        override fun getConnection(): Connection {
            return DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/demo", "root", "123456")
        }
    }
}

fun main() {

    val database = Database(getMySQLConnectionFactory())

    val users = Users()
    val addresses = Addresses()

    val list = database
        .select(users.id, users.username)
        .from(users)
        .fetchAll()
        .flat<User>()

    println(list)

}