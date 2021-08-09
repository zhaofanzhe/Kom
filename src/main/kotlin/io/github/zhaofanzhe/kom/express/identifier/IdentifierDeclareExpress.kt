package io.github.zhaofanzhe.kom.express.identifier

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class IdentifierDeclareExpress<T : Any>(
    private val identifier: Identifier<T>,
) : DeclareExpress<T>() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += identifier.identifier
        return result
    }
}