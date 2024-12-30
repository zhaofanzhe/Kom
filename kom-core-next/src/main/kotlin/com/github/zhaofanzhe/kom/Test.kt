package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.*
import com.github.zhaofanzhe.kom.expression.eq
import com.github.zhaofanzhe.kom.table.Table

class User {
    var id: Int = 0
    var name: String = ""
}

object Users : Table("users") {
    val id = column<Int>("id")
    val name = column<String>("name")
}

fun main() {

    val database = Database()

    var sql = database.from(Users).drop(10).take(10).sql

    println(sql)

    sql = database.from(Users)
        .where { Users.id eq 1 }
        .union {
            database.from(Users).where { Users.id eq 2 }
        }
        .unionAll {
            database.from(Users).where { Users.id eq 2 }
        }
        .drop(10)
        .take(10)
        .sql

    println(sql)

}