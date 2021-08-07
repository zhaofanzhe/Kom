package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class SortExpress(private val column: Column<*>,private val genre: Genre) : ExpressBuilder() {

    enum class Genre {
        ASC, DESC;

        override fun toString(): String {
            return when (this) {
                ASC -> "asc"
                DESC -> "desc"
            }
        }
    }

    override fun generate(context: Context) {
        val express = column.columnExpress()
        express.generate(context)
        expressBuilder.append(express.express())
        expressBuilder.append(' ')
        expressBuilder.append(genre)
    }

}