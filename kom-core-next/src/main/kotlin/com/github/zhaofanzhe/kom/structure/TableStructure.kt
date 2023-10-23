package com.github.zhaofanzhe.kom.structure

data class TableStructure(
    val name: String,
    val columns: List<ColumnStructure>,
    val primaryKey: List<String>,
    val indexes: Map<String, List<String>>,
)