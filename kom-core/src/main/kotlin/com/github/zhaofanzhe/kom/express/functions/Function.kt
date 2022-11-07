package com.github.zhaofanzhe.kom.express.functions

import com.github.zhaofanzhe.kom.express.Express
import com.github.zhaofanzhe.kom.express.ITable
import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.express.declare.DeclareExpress
import com.github.zhaofanzhe.kom.express.declare.DeclareRef

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
class Function<T : Any>(
    override val table: ITable<*>,
    private val functionName: String,
    private val functionArgs: Array<Any>,
    override val ref: Declare<T>? = null,
) : Declare<T> {

    override fun declare(): DeclareExpress {
        return FunctionDeclareExpress(this)
    }

    override fun express(): Express {
        return FunctionExpress(functionName, functionArgs)
    }

    override fun newTableDeclare(table: ITable<*>): Declare<T> {
        return DeclareRef(
            table = table,
            ref = this,
        )
    }

    override val name: String = "__col"

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(functionName)
        builder.append('(')
        functionArgs.forEachIndexed { index, value ->
            if (index > 0) {
                builder.append(", ")
            }
            if (value is Declare<*>) {
                var ref: Declare<*> = value
                while (true) {
                    if (ref.ref == null) break
                    ref = ref.ref!!
                }
                builder.append(ref.name)
            } else {
                builder.append("?")
            }
        }
        builder.append(')')
        return builder.toString()
    }

}