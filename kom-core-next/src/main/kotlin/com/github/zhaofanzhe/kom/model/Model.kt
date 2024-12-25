package com.github.zhaofanzhe.kom.model

import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.FieldDelegate
import kotlin.reflect.KProperty

abstract class Model(val tableName: String? = null, val primaryKey: KProperty<*>? = null)

fun int(primaryKey: Boolean = false) =
    FieldDelegate(0, Column("", "INT", false, primaryKey))

fun intNullable(primaryKey: Boolean = false) =
    FieldDelegate(null as Int?, Column("", "INT", true, primaryKey))

fun varchar(length: Int = 255) =
    FieldDelegate("", Column("", "VARCHAR", false, length = length))

fun varcharNullable(length: Int = 255) =
    FieldDelegate(null as String?, Column("", "VARCHAR", true, length = length))