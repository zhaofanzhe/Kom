package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.*
import com.github.zhaofanzhe.kom.dsl.express.desc
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.statement.from
import com.github.zhaofanzhe.kom.dsl.statement.leftJoin
import com.github.zhaofanzhe.kom.dsl.statement.orderBy
import com.github.zhaofanzhe.kom.dsl.statement.where

class Users : Table("users") {
    val id = int("id").primaryKey().autoIncrement().comment("ID")
    val username = varchar("username").index().comment("用户名")
    val password = varchar("password").index().comment("密码")
}

class Addresses : Table("addresses") {
    val id = int("id").primaryKey().autoIncrement().comment("ID")
    val userId = int("user_id").index().comment("用户ID")
    val province = varchar("province").comment("省")
    val city = varchar("city").comment("市")
    val district = varchar("district").comment("区")
}

fun main() {

    val database = Database()

    val users = Users()
    val addresses = Addresses()

    val statement =
        database.select(users.id, users.username, addresses.province, addresses.province, addresses.district)
            .from(users)
            .leftJoin(addresses, addresses.userId eq users.id)
            .where(useAnd {
                use(users.id eq 1.param)
            })
            .orderBy(users.id.desc)

    val bundle = statement.generateStatement()

    println(bundle.sql)
    println(bundle.args)

}