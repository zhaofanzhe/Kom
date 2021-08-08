package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.DeclareRefExpress
import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.ITable

class DeclareRef<T : Any>(
    override val table: ITable<*>,
    override val ref: Declare<T>? = null,
) : Declare<T> {

    override fun declare(): DeclareExpress<T> {
        return DeclareStatementExpress(this)
    }

    override fun express(): Express {
        return DeclareRefExpress(this)
    }

    override fun newTableDeclare(table: ITable<*>): Declare<T> {
        return DeclareRef(
            table = table,
            ref = this,
        )
    }

    override val name: String = "__col"

    override fun toString(): String {
        return ref.toString()
    }

}