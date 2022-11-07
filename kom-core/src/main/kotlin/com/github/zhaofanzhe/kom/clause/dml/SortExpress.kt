package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.Express
import com.github.zhaofanzhe.kom.express.ExpressResult

class SortExpress(private val column: Column<*, *>, private val genre: Genre) : Express() {

    enum class Genre {
        ASC, DESC;

        override fun toString(): String {
            return when (this) {
                ASC -> "asc"
                DESC -> "desc"
            }
        }
    }

    override fun generate(context: Context, result: ExpressResult) {
        column.express().generate(context, result)
        result += " "
        result += genre.toString()
    }

}