package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareStatementExpress
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class Column<U : Any, T : Any?>(
    override val name: String,
    val fieldName: String,
    val nullable: Boolean,
    override val table: ITable<*>,
    override val ref: Column<U, T>? = null,
) : Declare<T> {

    override fun declare(): DeclareExpress {
        return DeclareStatementExpress(this)
    }

    override fun express(): Express {
        return DeclareRefExpress(this)
    }

    override fun newTableDeclare(table: ITable<*>): Declare<T> {
        return Column(
            name = name,
            fieldName = fieldName,
            nullable = nullable,
            table = table,
            ref = this,
        )
    }

    override fun toString(): String {
        return name
    }

}