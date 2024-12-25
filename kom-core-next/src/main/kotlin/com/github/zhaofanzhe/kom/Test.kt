package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.model.*
import com.github.zhaofanzhe.kom.utils.TableUtil


class User : Model(primaryKey = User::id) {
    var id by int()
    var name by varchar(length = 100)
    var age by intNullable()
}

fun main() {
    val table = TableUtil.getTable(User::class)
    println("Table name: ${table.name}")
    table.columns.forEach {
        println("Column: ${it.name}, Type: ${it.type}, Nullable: ${it.nullable}, PrimaryKey: ${it.primaryKey}, Length: ${it.length}")
    }
}
