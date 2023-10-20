package com.github.zhaofanzhe.kom.structure

data class ColumnStructure(
    val name: String,
    val type: String,
    var isAutoIncrement: Boolean = false,
    var nullable: Boolean = false,
    var unique: Boolean = false,
    var comment: String? = null,
)