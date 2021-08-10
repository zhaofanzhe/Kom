package io.github.zhaofanzhe.kom.tool

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.reflect.KClass

object ColumnTool {

    private val zeroValues = mapOf<KClass<*>, Any>(
        Boolean::class to false,
        String::class to "",
        Byte::class to 0,
        Short::class to 0,
        Int::class to 0,
        Long::class to 0L,
        Float::class to 0.0,
        Double::class to 0.0,
        Date::class to Date(0),
        LocalDate::class to LocalDate.MIN,
        LocalDateTime::class to LocalDateTime.MIN,
        LocalTime::class to LocalTime.MIN,
    )

    fun isZeroValue(value: Any?): Boolean {
        if (value == null) return true
        return zeroValues[value::class] == value
    }

}