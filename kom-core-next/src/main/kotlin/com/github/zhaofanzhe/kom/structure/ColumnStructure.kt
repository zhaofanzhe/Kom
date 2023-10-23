package com.github.zhaofanzhe.kom.structure

data class ColumnStructure(
    val name: String,
    val type: String,
    val isPrimaryKey: Boolean = false,
    val isAutoIncrement: Boolean = false,
    val isNullable: Boolean = false,
    val isUnique: Boolean = false,
    val indexName: String? = null,
    val comment: String? = null,
)