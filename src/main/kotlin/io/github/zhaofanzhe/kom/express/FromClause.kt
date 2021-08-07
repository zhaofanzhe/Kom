package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

@Suppress("PrivatePropertyName")
class FromClause(private val table: Table<*>):ClauseExpressBuilder() {

    private var FROM  = "\nfrom "

    private var AS  = " as "

    override fun generate(context: Context) {
        super.generate(context)
        expressBuilder.append(FROM)
        expressBuilder.append(table.tableName())
        expressBuilder.append(AS)
        expressBuilder.append(context.tableAliasGenerator.next(table))
    }

}