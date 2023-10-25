package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.dsl.column.autoIncrement
import com.github.zhaofanzhe.kom.dsl.column.comment
import com.github.zhaofanzhe.kom.dsl.column.index
import com.github.zhaofanzhe.kom.dsl.column.primaryKey
import com.github.zhaofanzhe.kom.dsl.entity.Entity
import com.github.zhaofanzhe.kom.dsl.entity.delete
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.table.int
import com.github.zhaofanzhe.kom.dsl.table.varchar
import com.github.zhaofanzhe.kom.toolkit.KomToolkit
import com.github.zhaofanzhe.kom.toolkit.migrate
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
) : Entity<Users>(Users::class) {
    constructor() : this(id = 0, username = "", password = "")
}

class Users : Table("users") {
    val id = int("id").primaryKey().autoIncrement().comment("编号")
    val username = varchar("username").comment("用户名").index()
    val password = varchar("password").comment("密码").index()
}

class Addresses : Table("addresses") {
    var id = int("id").primaryKey().autoIncrement()
    var address = varchar("address").index()
    var userId = int("user_id").index()
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

    KomToolkit.migrate(database, users)

    database.delete(User()) { it.id eq 3.param }

    database.delete(User().also { it.id = 4 })

}