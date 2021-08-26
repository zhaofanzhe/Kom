package com.github.zhaofanzhe.kom.support.postgresql

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.flavor.Flavor
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
class PostgreSQLFlavor : Flavor {

    override val productName: String = "PostgreSQL"

    private val types = mapOf<KClass<*>, String>(
        Char::class to "char",
        String::class to "text",
        Byte::class to "smallint",
        Short::class to "smallint",
        Int::class to "int",
        Long::class to "bigint",
        Boolean::class to "boolean",
        Double::class to "double",
        Float::class to "float",
        Time::class to "datetime",
        LocalDate::class to "date",
        LocalDateTime::class to "datetime",
        LocalTime::class to "time",
    )

    private val serials = mapOf(
        "smallint" to "smallserial",
        "int" to "serial",
        "bigint" to "bigserial",
    )

    override fun typedef(column: Column<*, *>): String? {
        var type = types[column.clazz] ?: return null
        if (column.autoIncrement) {
            type = serials[type] ?: throw KomException("cant cast autoInc.")
        }
        return if (column.nullable) {
            "$type null default null"
        } else {
            "$type not null"
        }
    }

    private val reservedWords = listOf(
        "all", "analyse", "analyze", "and", "any",
        "array", "as", "asc", "asymmetric", "authorization",
        "binary", "both", "case", "cast", "check",
        "collate", "collation", "column", "concurrently",
        "constraint", "create", "cross", "current_catalog",
        "current_date", "current_role", "current_schema",
        "current_time", "current_timestamp", "current_user",
        "default", "deferrable", "desc", "distinct", "do",
        "else", "end", "except", "false", "fetch", "for",
        "foreign", "freeze", "from", "full", "grant", "group",
        "having", "ilike", "in", "initially", "inner", "intersect",
        "into", "is", "isnull", "join", "lateral", "leading",
        "left", "like", "limit", "localtime", "localtimestamp",
        "natural", "not", "notnull", "null", "offset", "on", "only",
        "or", "order", "outer", "overlaps", "placing", "primary",
        "references", "returning", "right", "select", "session_user",
        "similar", "some", "symmetric", "table", "tablesample", "then",
        "to", "trailing", "true", "union", "unique", "user",
        "using", "variadic", "verbose", "when", "where", "window", "with",
    )

    override fun name(name: String): String {
        if (reservedWords.contains(name.lowercase())) {
            return "\"${name}\""
        }
        return name
    }

}