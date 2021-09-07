package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.clause.ddl.AlterTableClause
import com.github.zhaofanzhe.kom.clause.ddl.CreateTableClause
import com.github.zhaofanzhe.kom.clause.ddl.DropTableClause
import com.github.zhaofanzhe.kom.clause.dml.DeleteClause
import com.github.zhaofanzhe.kom.clause.dml.InsertClause
import com.github.zhaofanzhe.kom.clause.dml.QueryClause
import com.github.zhaofanzhe.kom.clause.dml.UpdateClause
import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.entity.Entity
import com.github.zhaofanzhe.kom.express.*
import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.flavor.Flavor
import com.github.zhaofanzhe.kom.queryer.Queryer
import com.github.zhaofanzhe.kom.queryer.filler.TableFiller
import com.github.zhaofanzhe.kom.tool.ColumnTool
import com.github.zhaofanzhe.kom.toolkit.and
import com.github.zhaofanzhe.kom.toolkit.eq

@Suppress("UNCHECKED_CAST", "DuplicatedCode")
class Database(factory: ConnectionFactory) {

    private val queryer = Queryer(factory)

    private val flavor = Flavor.getFlavor(factory = factory)

    // DSL

    fun <T : Any> insert(table: Table<T>): InsertClause<T> {
        return InsertClause(queryer, flavor, table)
    }

    fun <T : Any> update(table: Table<T>): UpdateClause<T> {
        return UpdateClause(queryer, flavor, table)
    }

    fun <T : Any> delete(table: Table<T>): DeleteClause<T> {
        return DeleteClause(queryer, flavor, table)
    }

    private fun query(): QueryClause<*> {
        return QueryClause<Void>(
            queryer = queryer,
            flavor = flavor,
        )
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
        val result = express.executePrimaryKey()
        val filler = TableFiller(entity)
        primaryKeys.filter { it.autoIncrement }.forEach {
            filler.set(
                it, when (it.clazz) {
                    Byte::class -> result.toByte()
                    Short::class -> result.toShort()
                    Int::class -> result.toInt()
                    Long::class -> result
                    else -> throw KomException("argument type mismatch")
                }
            )
        }
        return true
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
                    if (ColumnTool.isZeroValue(primaryKey, values[primaryKey.fieldName])) {
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

    // ddl

    fun <T : Any> createTable(table: Table<T>): CreateTableClause<T> {
        return CreateTableClause(queryer, flavor, table)
    }

    fun <T : Any> dropTable(table: Table<T>): DropTableClause<T> {
        return DropTableClause(queryer, flavor, table)
    }

    fun <T : Any> alterTable(table: Table<T>): AlterTableClause<T> {
        return AlterTableClause(queryer, flavor, table)
    }

}