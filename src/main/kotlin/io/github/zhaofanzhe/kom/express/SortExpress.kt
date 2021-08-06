package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class SortExpress(column: Column<*>, genre: Genre) : ExpressBuilder() {

    enum class Genre {
        ASC, DESC;

        override fun toString(): String {
            return when (this) {
                ASC -> "asc"
                DESC -> "desc"
            }
        }
    }

    init {
        expressBuilder.append(column.columnName())
        expressBuilder.append(' ')
        expressBuilder.append(genre)
    }

}