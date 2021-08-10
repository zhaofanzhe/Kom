package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.clause.DeleteClause
import io.github.zhaofanzhe.kom.clause.InsertClause
import io.github.zhaofanzhe.kom.clause.QueryClause
import io.github.zhaofanzhe.kom.clause.UpdateClause
import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.entity.Entity
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.Queryer
import io.github.zhaofanzhe.kom.tool.ColumnTool
import io.github.zhaofanzhe.kom.toolkit.and
import io.github.zhaofanzhe.kom.toolkit.eq

@Suppress("UNCHECKED_CAST", "DuplicatedCode")
class Database(private val connectionFactory: ConnectionFactory) {

    private val queryer = Queryer(connectionFactory)

    // DSL

    fun <T : Any> insert(table: Table<T>): InsertClause<T> {
        return InsertClause(queryer, table)
    }

    fun <T : Any> update(table: Table<T>): UpdateClause<T> {
        return UpdateClause(queryer, table)
    }

    fun <T : Any> delete(table: Table<T>): DeleteClause<T> {
        return DeleteClause(queryer, table)
    }

    private fun query(): QueryClause<*> {
        return QueryClause<Void>(queryer)
    }

    fun <U : Any> select(table: ITable<U>): QueryClause<U> {
        return query().select(table)
    }

    fun select(vararg tables: ITable<*>): QueryClause<Tuple> {
        return query().select(*tables)
    }

    fun select(vararg declares: Declare<*>): QueryClause<Tuple> {
        return query().select(*declares)
    }

    fun <U : Any> selectFrom(clause: ITable<U>): QueryClause<U> {
        return query().select(clause).from(clause)
    }

    // Model CRUD

    fun create(entity: Entity<*>): Boolean {
        val table = entity.newTable()
        var columns = table.declares().map { it as Column<Any, Any?> }
        val values = entity.values()
        val primaryKeys = table.primaryKeys() as List<Column<Any, Any?>>
        primaryKeys.forEach { primaryKey ->
            // 过滤掉主键零值字段
            if (ColumnTool.isZeroValue(primaryKey, values[primaryKey.fieldName])) {
                columns = columns.filter { primaryKey != it }
            }
        }
        var express = insert(table) as InsertClause<Any>
        columns.forEach {
            express = express.set(it, values[it.fieldName])
        }
        return express.execute()
    }

    fun delete(entity: Entity<*>): Boolean {
        val table = entity.newTable()
        val values = entity.values()
        val primaryKeys = table.primaryKeys() as List<Column<Any, Any?>>

        if (primaryKeys.map { ColumnTool.isZeroValue(it, values[it.fieldName]) }.any { it }) {
            throw KomException("""has primaryKey is zero value.""")
        }

        return delete(table)
            .where(and {
                primaryKeys.forEach { primaryKey ->
                    and(primaryKey eq values[primaryKey.fieldName])
                }
            }).execute() == 1
    }

    fun save(entity: Entity<*>): Boolean {
        val table = entity.newTable()
        var columns = table.declares().map { it as Column<Any, Any?> }
        val values = entity.values()
        val primaryKeys = table.primaryKeys() as List<Column<Any, Any?>>

        // all primaryKey is zero value.
        if (primaryKeys.map { ColumnTool.isZeroValue(it, values[it.fieldName]) }.all { it }) {
            return create(entity)
        }

        columns = columns.filter { !primaryKeys.contains(it) }

        var express = update(table)
            .where(and {
                primaryKeys.forEach { primaryKey ->
                    if (!ColumnTool.isZeroValue(primaryKey, values[primaryKey.fieldName])) {
                        throw KomException("""primaryKey "$primaryKey" is a zero value.""")
                    }
                    and(primaryKey eq values[primaryKey.fieldName])
                }
            })

        columns.forEach {
            express = express.set(it, values[it.fieldName])
        }

        return express.execute() == 1
    }

    fun <T : Any, Q : Table<T>> fetchOne(table: Q, where: (Q) -> LogicExpress<Boolean>): T? {
        return selectFrom(table).where(where(table)).fetchOne()
    }

    fun <T : Any, Q : Table<T>> fetchAll(table: Q, where: (Q) -> LogicExpress<Boolean>): List<T> {
        return selectFrom(table).where(where(table)).fetchAll()
    }

}