package com.github.zhaofanzhe.kom.dsl.statement.dml

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.clause.WhereClause
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.Bundle

class DeleteStatement(
    internal val executor: Executor,
    internal val table: Table,
) : Statement {

    internal var where: WhereClause? = null

    override fun generateStatement(): Bundle {
        var sql = "delete from ${table.tableDefine()}\r\n"
        val args = mutableListOf<Any?>()

        if (where != null) {
            val bundle = where!!.generateClause()
            sql += bundle.sql
            args.addAll(bundle.args)
        }

        return Bundle(
            sql = sql,
            args = args,
        )
    }

}

fun DeleteStatement.where(clause: WhereClause): DeleteStatement {
    this.where = clause
    return this
}

fun DeleteStatement.where(express: Express<Boolean>): DeleteStatement {
    return where(WhereClause(express))
}

fun DeleteStatement.where(fn: () -> Express<Boolean>): DeleteStatement {
    return where(fn())
}

fun DeleteStatement.execute(): Int {
    return executor.execute(generateStatement())
}