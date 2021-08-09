package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.*

class SortExpress(private val column: Column<*,*>, private val genre: Genre) : Express() {

    enum class Genre {
        ASC, DESC;

        override fun toString(): String {
            return when (this) {
                ASC -> "asc"
                DESC -> "desc"
            }
        }
    }

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += column.express().generate(context)
        result += " "
        result += genre.toString()
        return result
    }

}