package com.github.zhaofanzhe.kom.support.mysql

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.flavor.Flavor
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
open class MySQLFlavor : Flavor {

    override val productName: String = "MySQL"

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

    @Suppress("UNCHECKED_CAST")
    override fun typedef(column: Column<*, *>, primaryKeySize: Int): String? {
        val type = types[column.clazz] ?: return null
        val autoIncrement = if (column.autoIncrement) {
            if (!column.primaryKey) {
                throw KomException("auto increment required primary key.")
            }
            if ((column.table.declares() as List<Column<*, *>>).filter { it.autoIncrement }.count() > 1) {
                throw KomException("there can be only one auto column.")
            }
            "auto_increment"
        } else {
            null
        }
        val nullable = if (column.nullable) {
            "null"
        } else {
            "not null"
        }
        val primaryKey = if (column.primaryKey && primaryKeySize == 1) {
            "primary key"
        } else {
            null
        }
        return listOfNotNull(type, autoIncrement, nullable, primaryKey).joinToString(separator = " ")
    }

    private val reservedWords = arrayOf(
        "accessible", "add", "all", "alter", "analyze", "and",
        "as", "asc", "asensitive", "before", "between", "bigint",
        "binary", "blob", "both", "by", "call", "cascade",
        "case", "change", "char", "character", "check", "collate",
        "column", "condition", "constraint", "continue", "convert",
        "create", "cross", "cube", "cume_dist", "current_date",
        "current_time", "current_timestamp", "current_user",
        "utc_date", "utc_time", "utc_timestamp", "values",
        "varbinary", "varchar", "varcharacter", "varying",
        "virtual", "when", "where", "while", "window", "with",
        "write", "xor", "year_month", "zerofill", "unsigned",
        "update", "usage", "use", "using", "cursor", "database",
        "databases", "day_hour", "day_microsecond", "day_minute",
        "day_second", "dec", "decimal", "declare", "default", "delayed",
        "delete", "dense_rank", "desc", "describe", "deterministic",
        "else", "elseif", "empty", "enclosed", "load", "localtime",
        "localtimestamp", "lock", "long", "longblob", "longtext",
        "loop", "low_priority", "master_bind", "master_ssl_verify_server_cert",
        "match", "maxvalue", "mediumblob", "mediumint", "distinct",
        "distinctrow", "div", "double", "drop", "dual", "each",
        "escaped", "except", "exists", "exit", "explain", "false",
        "fetch", "first_value", "float", "float4", "float8",
        "for", "force", "foreign", "from", "fulltext", "function",
        "generated", "get", "grant", "group", "grouping", "groups",
        "having", "high_priority", "hour_microsecond", "hour_minute",
        "hour_second", "if", "ignore", "in", "index", "infile",
        "inner", "inout", "insensitive", "insert", "int", "int1",
        "int2", "int3", "int4", "int8", "integer", "interval",
        "into", "io_after_gtids", "io_before_gtids", "is",
        "iterate", "primary", "procedure", "purge", "range", "rank",
        "read", "reads", "read_write", "real", "recursive", "references",
        "regexp", "release", "rename", "repeat", "replace", "require",
        "resignal", "restrict", "return", "revoke", "right", "rlike", "row",
        "rows", "mediumtext", "middleint", "minute_microsecond", "minute_second",
        "mod", "modifies", "natural", "not", "no_write_to_binlog", "nth_value",
        "ntile", "null", "numeric", "of", "on", "optimize", "optimizer_costs",
        "option", "optionally", "or", "join", "json_table", "key", "keys",
        "kill", "lag", "last_value", "lateral", "lead", "row_number",
        "schema", "schemas", "second_microsecond", "select", "sensitive",
        "separator", "set", "show", "signal", "smallint", "leading",
        "leave", "left", "like", "limit", "linear", "lines", "order",
        "out", "outer", "outfile", "over", "partition", "percent_rank",
        "precision", "spatial", "specific", "sql", "sqlexception",
        "sqlstate", "sqlwarning", "sql_big_result", "sql_calc_found_rows",
        "sql_small_result", "ssl", "starting", "stored", "straight_join",
        "system", "table", "terminated", "then", "tinyblob", "tinyint",
        "tinytext", "to", "trailing", "trigger", "true", "undo", "union",
        "unique", "unlock", "cume_dist", "dense_rank", "empty", "except",
        "first_value", "grouping", "groups", "json_table", "lag", "last_value",
        "lateral", "lead", "nth_value", "ntile", "of", "over", "percent_rank",
        "rank", "recursive", "row_number", "system", "window"
    )

    override fun name(name: String): String {
        if (reservedWords.contains(name.lowercase())) {
            return "`${name}`"
        }
        return name
    }

}