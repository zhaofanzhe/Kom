package io.github.zhaofanzhe.kom.example

import io.github.zhaofanzhe.kom.Database
import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.entity.Entity
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.toolkit.eq
import io.github.zhaofanzhe.kom.toolkit.like
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    val database = getDatabase()

    // 创建表[目前不支持自动迁移]
    database.createTable(Users()).execute()
    database.createTable(Addresses()).execute()

    // 单表CRUD
    // 目前插入数据，目前没有做主键的回填
    database.create(User(id = 1, username = "张三"))
    database.create(User(id = 2, username = "李四"))
    database.create(User(id = 3, username = "王五"))
    database.create(Address(address = "北京", userId = 1))
    database.create(Address(address = "上海", userId = 1))
    database.create(Address(address = "广州", userId = 2))
    database.create(Address(address = "深圳", userId = 3))

    val user = database.fetchOne(Users()) { it.username eq "王五" }

    // 根据主键更新
    user?.let {
        it.username = "王五2"
        database.save(it)
    }

    // 根据主键删除
    user?.let {
        database.delete(it)
    }

    // DSL 查询

    val users = Users()

    val addresses = Addresses()

    // 子查询
    val subQuery = database.selectFrom(users)
        .where(users.username like "%张%")
        .subQuery()

    // 左连接
    val express = database.select(users, addresses)
        .from(subQuery)
        .leftJoin(addresses).on(addresses.userId eq users.id)

    // 打印SQL
    println(express)

    // 遍历获取数据
    express.fetchAll().forEach {
        println(it[users])
        println(it[addresses])
    }

    // 删除表
//    database.dropTable(Users()).execute()
//    database.dropTable(Addresses()).execute()

}

data class User(
    var id: Int = 0,
    var username: String = "",
) : Entity<Users>(Users::class) {
    constructor() : this(id = 0, username = "")
}

class Users : Table<User>(User::class) {
    val id = column(User::id).primaryKey().autoIncrement()
    val username = column(User::username).unique()
}

data class Address(
    var id: Int = 0,
    var address: String = "",
    var userId: Int = 0,
) : Entity<Addresses>(Addresses::class) {
    constructor() : this(id = 0, address = "", userId = 0)
}

class Addresses : Table<Address>(Address::class) {
    var id = column(Address::id).primaryKey().autoIncrement()
    var address = column(Address::address)
    var userId = column(Address::userId)
}

fun getDatabase(): Database {
    return Database(object : ConnectionFactory {
        override fun getConnection(): Connection {
            return DriverManager.getConnection("{url}", "{user}", "{password}")
        }
    })
}
