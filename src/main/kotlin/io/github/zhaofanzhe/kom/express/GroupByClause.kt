package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

@Suppress("PrivatePropertyName")
class GroupByClause(private vararg val columns: Column<*>) : ClauseExpressBuilder() {

    private val GROUP_BY = "\ngroup by "

    override fun generate(context: Context) {
        super.generate(context)
        if (columns.isNotEmpty()) {
            expressBuilder.append(GROUP_BY)
            val merge = ExpressMerge(*columns.map { it.columnExpress() }.toTypedArray())
            merge.generate(context)
            expressBuilder.append(merge.express())
        }
    }

}