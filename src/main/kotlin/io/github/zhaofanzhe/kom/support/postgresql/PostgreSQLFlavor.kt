package io.github.zhaofanzhe.kom.support.postgresql

import io.github.zhaofanzhe.kom.flavor.Flavor
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
class PostgreSQLFlavor : Flavor {

    private val types = mapOf<KClass<*>, String>(
        Char::class to "char",
        String::class to "varchar(255)",
        Byte::class to "int(11)",
        Short::class to "int(11)",
        Int::class to "int(11)",
        Long::class to "int(11)",
        Boolean::class to "boolean",
        Double::class to "double",
        Float::class to "float",
        Time::class to "datetime",
        LocalDate::class to "date",
        LocalDateTime::class to "datetime",
        LocalTime::class to "time",
    )

    override fun types(): Map<KClass<*>, String> {
        return types
    }

    override fun type(type: KClass<*>): String? {
        return types[type]
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

    override fun tableName(table: String): String {
        if (reservedWords.contains(table.lowercase())) {
            println("[warring] table name \"$table\" is reserved.")
            return "\"${table}\""
        }
        return table
    }

}