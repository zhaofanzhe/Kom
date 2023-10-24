package com.github.zhaofanzhe.kom.structure

data class ColumnStructure(
    val name: String,
    val type: String,
    val isAutoIncrement: Boolean = false,
    val isNullable: Boolean = false,
    val comment: String? = null,
)