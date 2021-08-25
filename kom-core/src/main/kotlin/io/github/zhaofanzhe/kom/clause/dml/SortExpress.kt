package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.express.*

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