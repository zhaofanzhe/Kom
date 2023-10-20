package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.column.autoIncrement
import com.github.zhaofanzhe.kom.dsl.column.comment
import com.github.zhaofanzhe.kom.dsl.column.index
import com.github.zhaofanzhe.kom.dsl.column.primaryKey
import com.github.zhaofanzhe.kom.dsl.express.gt
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.function.count
import com.github.zhaofanzhe.kom.dsl.selectable.alias
import com.github.zhaofanzhe.kom.dsl.statement.dml.from
import com.github.zhaofanzhe.kom.dsl.statement.dml.groupBy
import com.github.zhaofanzhe.kom.dsl.statement.dml.having
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.table.int
import com.github.zhaofanzhe.kom.dsl.table.varchar

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

    val addresses = Addresses()

    val statement = database
        .select(
            addresses.userId.alias("用户ID"),
            count(addresses.id).alias("地址数量"),
        )
        .from(addresses)
        .groupBy(addresses.userId)
        .having(count(addresses.id) gt 2.param)

    val bundle = statement.generateStatement()

    println(bundle.sql)
    println(bundle.args)

}