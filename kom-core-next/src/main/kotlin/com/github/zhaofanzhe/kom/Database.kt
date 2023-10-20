package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.statement.dml.QueryStatement
import com.github.zhaofanzhe.kom.dsl.statement.dml.select

class Database {
}

fun Database.select(vararg selectables: Selectable): QueryStatement {
    return QueryStatement().apply { select(*selectables) }
}
