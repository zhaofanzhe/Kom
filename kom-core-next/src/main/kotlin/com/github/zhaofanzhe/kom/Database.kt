package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.core.Selectable
import com.github.zhaofanzhe.kom.dsl.statement.QueryStatement
import com.github.zhaofanzhe.kom.dsl.statement.select

class Database {
}

fun Database.select(vararg selectables: Selectable): QueryStatement {
    return QueryStatement().apply { select(*selectables) }
}
