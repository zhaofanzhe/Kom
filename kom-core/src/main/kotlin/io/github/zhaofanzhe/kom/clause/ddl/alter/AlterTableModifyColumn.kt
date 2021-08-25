package io.github.zhaofanzhe.kom.clause.ddl.alter

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.flavor.Flavor

class AlterTableModifyColumn<T : Any>(
    private val flavor: Flavor,
    private val column: Column<T, *>,
):AlterSubClause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "modify "
        result += flavor.name(column.name)
        result += " "
        result += flavor.typedef(column) ?: throw KomException("Unable to resolve class ${column.clazz}.")
    }

}