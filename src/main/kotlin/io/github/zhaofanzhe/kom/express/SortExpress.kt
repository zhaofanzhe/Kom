package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class SortExpress(field: Field<*>, genre: Genre) : ExpressBuilder() {

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
        expressBuilder.append(field.columnName())
        expressBuilder.append(' ')
        expressBuilder.append(genre)
    }

}