package io.github.zhaofanzhe.kom.support.mysql

import io.github.zhaofanzhe.kom.flavor.Flavor
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
open class MySQLFlavor : Flavor {

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

    override fun tableName(table: String): String {
        return "`${table}`"
    }

}