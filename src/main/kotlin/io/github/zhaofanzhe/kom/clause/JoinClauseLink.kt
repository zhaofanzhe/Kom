package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.ITable

interface JoinClauseLink<Q:Clause> {

    fun join(clause: JoinClause<Q>, table: ITable<*>): Q

}