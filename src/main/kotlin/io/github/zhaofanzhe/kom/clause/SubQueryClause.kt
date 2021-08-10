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

    override fun generate(context: Context, result: ExpressResult) {
        result += "("
        generateUnenclosed(context, result)
        result += ")"
    }

    fun generateUnenclosed(context: Context, result: ExpressResult) {
        express.generate(context, result)
    }

    override fun refs(): List<ITable<*>> {
        return tables
    }

    override fun primaryKeys(): List<Column<T, *>> {
        return emptyList()
    }

}