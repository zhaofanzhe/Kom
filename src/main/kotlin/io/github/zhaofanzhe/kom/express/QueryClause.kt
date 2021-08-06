package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.queryer.Queryer
import io.github.zhaofanzhe.kom.tool.Computable
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class QueryClause<T : Any>(private val queryer: Queryer) : Clause() {

    private val computable = Computable {
        val list = mutableListOf(select, from, where, groupBy, orderBy)
        if (limit != null) {
            list.add(limit)
            if (offset != null) {
                list.add(offset)
            }
        }
        ExpressMerge(*list.toTypedArray())
    }

    private var select: SelectClause? by computable.observable(null)

    private var from: FromClause? by computable.observable(null)

    private var where: WhereClause? by computable.observable(null)

    private var groupBy: GroupByClause? by computable.observable(null)

    private var orderBy: OrderByClause? by computable.observable(null)

    private var limit: LimitClause? by computable.observable(null)

    private var offset: OffsetClause? by computable.observable(null)

    private var fromKClass: KClass<T>? = null

    private val queryClause: Express by computable

    fun <U:Any> select(entity: Entity<U>): QueryClause<U> {
        this.select = SelectClause(*entity.declares())
        this.fromKClass = entity.entityClass() as KClass<T>
        return this as QueryClause<U>
    }

    fun select(vararg fields: Field<*>): QueryClause<Tuple> {
        this.select = SelectClause(*fields.map { DeclareExpress(it) }.toTypedArray())
        this.fromKClass = Tuple::class as KClass<T>
        return this as QueryClause<Tuple>
    }

    fun from(entity: Entity<*>): QueryClause<T> {
        this.from = FromClause(entity)
        return this
    }

    fun where(clause: LogicExpress): QueryClause<T> {
        this.where = WhereClause(clause)
        return this
    }

    fun groupBy(vararg fields: Field<*>): QueryClause<T> {
        this.groupBy = GroupByClause(*fields)
        return this
    }

    fun orderBy(vararg sorts: SortExpress): QueryClause<T> {
        this.orderBy = OrderByClause(*sorts)
        return this
    }

    fun limit(limitSize: Long): QueryClause<T> {
        this.limit = LimitClause(limitSize)
        return this
    }

    fun limit(limitSize: Long, offsetSize: Long): QueryClause<T> {
        return limit(limitSize).offset(offsetSize)
    }

    fun offset(offsetSize: Long): QueryClause<T> {
        this.offset = OffsetClause(offsetSize)
        return this
    }

    fun fetchAll(): List<T> {
        return queryer.executeQuery(express(), params().toList()).fetchAll(this.fromKClass!!)
    }

    private fun totalExpress(): Express {
        return ExpressMerge(select, from, where)
    }

    override fun express(): String {
        return queryClause.express()
    }

    override fun params(): Array<Any> {
        return queryClause.params()
    }

}