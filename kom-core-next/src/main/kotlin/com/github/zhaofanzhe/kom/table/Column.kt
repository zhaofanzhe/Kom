package com.github.zhaofanzhe.kom.table

// 定义字段约束
data class Column(
    val name: String,    // 字段名
    val type: String,    // 字段类型，如 INT, VARCHAR 等
    val nullable: Boolean = true, // 是否可以为 null
    val primaryKey: Boolean = false, // 是否是主键
    val length: Int? = null // 字段长度，适用于 varchar 等类型
)