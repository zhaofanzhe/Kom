package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare

class DeclareRefExpress(
    private val declare: Declare<*>,
): Express() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += context.currentTableAlias(declare.table)
        result += "."
        result += context.currentDeclareName(declare)
        return result
    }

}