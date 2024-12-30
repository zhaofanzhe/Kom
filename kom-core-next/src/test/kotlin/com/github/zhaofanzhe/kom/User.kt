package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.table.Table

class User {
    var id: Int = 0
    var name: String = ""
}

object Users : Table("users") {
    val id = column<Int>("id")
    val name = column<String>("name")
}
