package io.github.zhaofanzhe.kom.express.identifier

import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.ITable
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class Identifier<T : Any>(
    override val table: ITable<*>,
    val identifier: String,
) : Declare<T> {

    override fun declare(): DeclareExpress<T> {
        return IdentifierDeclareExpress(this)
    }

    override fun express(): Express {
        return IdentifierDeclareExpress(this)
    }

    override fun newTableDeclare(table: ITable<*>): Declare<T> {
        return this
    }

    override val ref: Declare<T>? = null

    override val name: String = "__col_all"

}