package com.github.zhaofanzhe.kom.dsl.table

import com.github.zhaofanzhe.kom.dsl.column.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

open class Table(
    internal val name: String,
    internal var alias: String? = null,
) : TableRef {

    internal val columns: MutableList<Column<*>> = mutableListOf()

    internal val primaryKey: MutableList<Column<*>> = mutableListOf()

    internal val indexes: MutableMap<String, MutableList<Column<*>>> = mutableMapOf()

    override fun tableDefine(): String {
        if (this.alias != null) {
            return "`${this.name}` as `${this.alias}`"
        }
        return "`${this.name}`"
    }

    override fun tableName(): String {
        if (this.alias != null) {
            return "`${this.alias}`"
        }
        return "`${this.name}`"
    }

}

inline fun <reified T : Table> T.alias(aliasName: String): T {
    return alias(T::class, aliasName)
}

fun <T : Table> T.alias(clazz: KClass<T>, aliasName: String): T {
    val constructor = clazz.constructors.find { it.parameters.isEmpty() } ?: throw RuntimeException("未找到默认构造器")
    val table = constructor.call()
    table.alias = aliasName
    return table
}

fun <R> Table.column(name: String, type: String): Column<R> {
    val column = Column<R>(this, name, type)
    this.columns += column
    return column
}

fun Table.varchar(name: String, size: Int = 256): Column<String> {
    return column(name, "varchar(${size})")
}

fun Table.char(name: String, size: Int = 256): Column<String> {
    return column(name, "char(${size})")
}

fun Table.int(name: String, size: Int = 11): Column<Int> {
    return column(name, "int(${size})")
}

fun Table.decimal(name: String, precision: Int = 13, scale: Int = 2): Column<BigDecimal> {
    return column(name, "decimal(${precision},${scale})")
}

fun Table.date(name: String): Column<LocalDate> {
    return column(name, "date")
}

fun Table.time(name: String): Column<LocalTime> {
    return column(name, "time")
}

fun Table.datetime(name: String): Column<LocalDateTime> {
    return column(name, "datetime")
}

