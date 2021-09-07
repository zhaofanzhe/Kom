package com.github.zhaofanzhe.kom.support.sqlite

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.flavor.Flavor
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
class SQLiteFlavor : Flavor {

    override val productName: String = "SQLite"

    private val types = mapOf<KClass<*>, String>(
        Char::class to "text",
        String::class to "text",
        Byte::class to "integer",
        Short::class to "integer",
        Int::class to "integer",
        Long::class to "integer",
        Boolean::class to "integer",
        Double::class to "real",
        Float::class to "real",
        Time::class to "text",
        LocalDate::class to "text",
        LocalDateTime::class to "text",
        LocalTime::class to "text",
    )

    override fun typedef(column: Column<*, *>): String? {
        val type = types[column.clazz] ?: return null
        return if (column.nullable) {
            "$type null default null"
        } else {
            "$type not null"
        }
    }

    override fun name(name: String): String {
        return name
    }

}