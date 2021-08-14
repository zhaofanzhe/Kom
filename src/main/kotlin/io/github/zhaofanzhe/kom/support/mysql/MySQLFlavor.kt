package io.github.zhaofanzhe.kom.support.mysql

import io.github.zhaofanzhe.kom.express.Column
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
        Byte::class to "tinyint",
        Short::class to "smallint",
        Int::class to "integer",
        Long::class to "bigint",
        Boolean::class to "boolean",
        Double::class to "double",
        Float::class to "float",
        Time::class to "datetime",
        LocalDate::class to "date",
        LocalDateTime::class to "datetime",
        LocalTime::class to "time",
    )

    override fun typedef(column: Column<*, *>): String? {
        val type = types[column.clazz] ?: return null
        val autoIncrement = if (column.autoInc){
            "auto_increment"
        } else {
            null
        }
        val nullable = if (column.nullable){
            "default null"
        } else {
            "not null"
        }
        return listOfNotNull(type, autoIncrement, nullable).joinToString(separator = " ")
    }

    override fun name(name: String): String {
        return "`${name}`"
    }

}