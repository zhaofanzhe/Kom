package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SubQueryClause<T : Any>(
    private val express: Express,
    declares: List<Declare<*>>,
    override val entityClass: KClass<T> = Tuple::class as KClass<T>,
    private val tables: List<ITable<*>>,
) : Clause(), ITable<T> {

    private val declares: List<Declare<*>> = declares.map { it.newTableDeclare(this) }

    override val tableName: String = "__temp"

    override fun declares(): List<Declare<*>> {
        return this.declares
    }

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += "("
        result += express.generate(context)
        result += ")"
        return result
    }

    override fun refs(): List<ITable<*>> {
        return tables
    }

}