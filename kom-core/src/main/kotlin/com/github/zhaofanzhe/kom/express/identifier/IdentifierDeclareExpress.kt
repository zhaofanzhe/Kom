package com.github.zhaofanzhe.kom.express.identifier

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.declare.DeclareExpress

class IdentifierDeclareExpress<T : Any>(
    private val identifier: Identifier<T>,
) : DeclareExpress() {

    override fun generate(context: Context, result: ExpressResult) {
        result += identifier.identifier
    }
}