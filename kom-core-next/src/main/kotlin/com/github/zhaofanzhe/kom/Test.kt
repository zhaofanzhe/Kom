package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.expression.and
import com.github.zhaofanzhe.kom.expression.eq
import com.github.zhaofanzhe.kom.expression.gt
import com.github.zhaofanzhe.kom.expression.select
import com.github.zhaofanzhe.kom.table.Table


object Users : Table("users") {
    val id = column<String>("id")
    val name = column<String>("name")
    val age = column<Int>("age")
}


fun main() {
    val sql = select(Users.id, Users.name, Users.age)
        .from(Users)
        .where { (Users.age gt 18) and (Users.name eq "tom") }
    println(sql)
}
