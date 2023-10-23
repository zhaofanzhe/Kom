package com.github.zhaofanzhe.kom.dsl.statement.dml

import com.github.zhaofanzhe.kom.core.*
import com.github.zhaofanzhe.kom.dsl.clause.*
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.express.SortExpress
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.TableRef
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.mergeBundles
import com.github.zhaofanzhe.kom.exception.KomException

class QueryStatement(
    internal val executor: Executor
) : Statement {

    internal var select: SelectClause? = null

    internal var from: FromClause? = null

    internal val joins: MutableList<JoinClause> = mutableListOf()

    internal var where: WhereClause? = null

    internal var groupBy: GroupByClause? = null

    internal var having: HavingClause? = null

    internal var orderBy: OrderByClause? = null

    internal var limit: LimitClause? = null

    override fun generateStatement(): Bundle {
        val clauses = mutableListOf<Clause>()
        select?.let { clauses += it }
        from?.let { clauses += it }
        clauses += joins
        where?.let { clauses += it }
        groupBy?.let { clauses += it }
        having?.let { clauses += it }
        orderBy?.let { clauses += it }
        limit?.let { clauses += it }
        return mergeBundles(*clauses.map { it.generateClause() }.toTypedArray(), separator = "\r\n")
    }

}

internal fun QueryStatement.select(clause: SelectClause): QueryStatement {
    this.select = clause
    return this
}

fun QueryStatement.select(vararg selectables: Selectable): QueryStatement {
    return select(SelectClause(selectables.toList()))
}

internal fun QueryStatement.from(clause: FromClause): QueryStatement {
    this.from = clause
    return this
}

internal fun QueryStatement.from(tableRef: TableRef): QueryStatement {
    return from(FromClause(tableRef))
}

internal fun QueryStatement.join(clause: JoinClause): QueryStatement {
    this.joins += clause
    return this
}

internal fun QueryStatement.join(joinType: String, tableRef: TableRef, express: Express<Boolean>): QueryStatement {
    return join(JoinClause(joinType, tableRef, express))
}

fun QueryStatement.leftJoin(tableRef: TableRef, express: Express<Boolean>): QueryStatement {
    return join("left join", tableRef, express)
}

fun QueryStatement.leftJoin(tableRef: TableRef, fn: () -> Express<Boolean>): QueryStatement {
    return join("left join", tableRef, fn())
}

fun QueryStatement.rightJoin(tableRef: TableRef, express: Express<Boolean>): QueryStatement {
    return join("right join", tableRef, express)
}

fun QueryStatement.rightJoin(tableRef: TableRef, fn: () -> Express<Boolean>): QueryStatement {
    return join("right join", tableRef, fn())
}

internal fun QueryStatement.where(clause: WhereClause): QueryStatement {
    this.where = clause
    return this
}

fun QueryStatement.where(express: Express<Boolean>): QueryStatement {
    return where(WhereClause(express))
}

fun QueryStatement.where(fn: () -> Express<Boolean>): QueryStatement {
    return where(fn())
}

internal fun QueryStatement.groupBy(clause: GroupByClause): QueryStatement {
    this.groupBy = clause
    return this
}

fun QueryStatement.groupBy(vararg columns: Column<*>): QueryStatement {
    return groupBy(GroupByClause(columns.toList()))
}

internal fun QueryStatement.having(clause: HavingClause): QueryStatement {
    this.having = clause
    return this
}

fun QueryStatement.having(express: Express<Boolean>): QueryStatement {
    return having(HavingClause(express))
}

fun QueryStatement.having(fn: () -> Express<Boolean>): QueryStatement {
    return having(fn())
}

internal fun QueryStatement.orderBy(clause: OrderByClause): QueryStatement {
    this.orderBy = clause
    return this
}

fun QueryStatement.orderBy(vararg expresses: SortExpress): QueryStatement {
    return orderBy(OrderByClause(*expresses))
}

fun QueryStatement.limit(clause: LimitClause): QueryStatement {
    this.limit = clause
    return this
}

fun QueryStatement.limit(offset: Int, limit: Int): QueryStatement {
    if (offset < 0) {
        throw KomException("offset 不能小于 0")
    }
    if (limit <= 0) {
        throw KomException("limit 不能小于等于 0")
    }
    return limit(LimitClause(offset, limit))
}

fun QueryStatement.limit(limit: Int): QueryStatement {
    return limit(0, limit)
}

internal fun QueryStatement.execute(): QueryExecutor {
    if (select == null) {
        throw KomException("select 不能为空")
    }
    if (from == null) {
        throw KomException("from 不能为空")
    }
    val bundle = generateStatement()
    return this.executor.executeQuery(bundle, select!!.selectables)
}

fun QueryStatement.fetchOne(): QueryResult? {
    return this.execute().fetchOne()
}

fun QueryStatement.fetchAll(): List<QueryResult> {
    return this.execute().fetchAll()
}