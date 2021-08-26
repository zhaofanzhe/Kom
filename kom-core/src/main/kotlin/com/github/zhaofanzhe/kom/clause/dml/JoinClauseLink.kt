package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.ITable

interface JoinClauseLink<Q: Clause> {

    fun join(clause: JoinClause<Q>, table: ITable<*>): Q

}