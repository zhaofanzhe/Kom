package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column

class SortExpress(
    val column: Column<*>,
    val sort: Sort,
) : Express<Column<*>> {

    enum class Sort {
        ASC, DESC;

        override fun toString(): String = when (this) {
            ASC -> "asc"
            DESC -> "desc"
        }

    }

    override fun generateExpress(): Bundle {
        return Bundle(
            sql = "${this.column.generateSelectable().sql} ${this.sort}"
        )
    }

}

val Column<*>.asc: SortExpress
    get() = SortExpress(this, SortExpress.Sort.ASC)

val Column<*>.desc: SortExpress
    get() = SortExpress(this, SortExpress.Sort.DESC)