package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.model.*


class User : Model {
    var id by int() // 主键
    var name by varchar(length = 100) // 非空字段
    var age by intOrNull() // 非空字段
}

// 测试代码
fun main() {
    // 获取 User 类的 Table 实例
    val table = Model.getTable(User::class)
    // 输出表结构
    println("Table name: ${table.name}")
    table.columns.forEach {
        println("Column: ${it.name}, Type: ${it.type}, Nullable: ${it.nullable}, PrimaryKey: ${it.primaryKey}, Length: ${it.length}")
    }
}
