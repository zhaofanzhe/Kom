package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.ITable

interface JoinClauseLink<Q: Clause> {

    fun join(clause: JoinClause<Q>, table: ITable<*>): Q

}