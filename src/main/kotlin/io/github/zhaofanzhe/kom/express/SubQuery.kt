package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SubQuery<T : Any>(
    private val clause: QueryClause<T>
) : ExpressBuilder(), ITable<T> {

    private val declares: List<Declare<*>> = clause.declares?.map { it.clone(this) } ?: emptyList()

    private val source: Any? = clause.source

    override fun generate(context: Context) {
        super.generate(context)

        clause.generate(context)

        expressBuilder.append('(')
        expressBuilder.append(clause.express())
        expressBuilder.append(')')

        paramsBuilder.addAll(clause.params())
    }

    override fun declares(): List<Declare<*>> {
        return declares
    }

    override fun declareExpress(): Array<DeclareExpress> {
        return declares.map { it.declareExpress() }.toTypedArray()
    }

    override fun tableName(): String {
        return "temp"
    }

    override fun entityClass(): KClass<T> {
        return if (source is ITable<*>) {
            return (source as ITable<T>).entityClass()
        } else {
            source
        } as KClass<T>
    }

    override fun source(): Any {
        val entityClass = this.entityClass()
        if (entityClass == Tuple::class){
            return entityClass
        }
        return this
    }

}