package io.github.zhaofanzhe.kom.express.identifier

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class IdentifierDeclareExpress<T : Any>(
    private val identifier: Identifier<T>,
) : DeclareExpress() {

    override fun generate(context: Context, result: ExpressResult) {
        result += identifier.identifier
    }
}