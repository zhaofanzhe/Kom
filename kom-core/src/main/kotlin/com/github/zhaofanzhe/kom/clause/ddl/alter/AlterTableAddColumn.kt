package com.github.zhaofanzhe.kom.clause.ddl.alter

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.flavor.Flavor

class AlterTableAddColumn<T : Any>(
    private val flavor: Flavor,
    private val column: Column<T, *>,
) : AlterSubClause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "add "
        result += flavor.name(column.name)
        result += " "
        result += flavor.typedef(column, column.table.primaryKeys().size)
            ?: throw KomException("Unable to resolve class ${column.clazz}.")
    }

}