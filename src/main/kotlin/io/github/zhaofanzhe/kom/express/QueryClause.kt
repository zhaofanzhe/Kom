package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.queryer.QuerySource
import io.github.zhaofanzhe.kom.queryer.Queryer
import io.github.zhaofanzhe.kom.tool.Computable

@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class QueryClause<T : Any>(private val queryer: Queryer) : Clause() {

    private lateinit var context: Context

    private val computable = Computable {
        context = Context()
        merge(context)
    }

    private var select: SelectClause? by computable.observable(null)

    private var from: FromClause? by computable.observable(null)

    private var where: WhereClause? by computable.observable(null)

    private var groupBy: GroupByClause? by computable.observable(null)

    private var orderBy: OrderByClause? by computable.observable(null)

    private var limit: LimitClause? by computable.observable(null)

    private var offset: OffsetClause? by computable.observable(null)

    private var joins: MutableList<JoinClause<*>> by computable.observable(mutableListOf())

    private var source: Any? = null

    private val queryClause: Express by computable

    fun <U : Any> select(table: Table<U>): QueryClause<U> {
        this.select = SelectClause(*table.declares())
        this.source = table
        return this as QueryClause<U>
    }

    fun select(vararg tables: Table<*>): QueryClause<Tuple> {
        val declares = mutableListOf<DeclareExpress>()
        tables.forEach {
            declares.addAll(it.declares())
        }
        this.select = SelectClause(*declares.toTypedArray())
        this.source = Tuple::class
        return this as QueryClause<Tuple>
    }

    fun select(vararg columns: Column<*>): QueryClause<Tuple> {
        this.select = SelectClause(*columns.map { DeclareExpress(it) }.toTypedArray())
        this.source = Tuple::class
        return this as QueryClause<Tuple>
    }

    fun from(table: Table<*>): QueryClause<T> {
        this.from = FromClause(table)
        return this
    }

    internal fun join(clause: JoinClause<*>): QueryClause<T> {
        joins.add(clause)
        return this
    }

    fun innerJoin(table: Table<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.INNER, table, this)
    }

    fun leftJoin(table: Table<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.LEFT, table, this)
    }

    fun rightJoin(table: Table<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.RIGHT, table, this)
    }

    fun fullJoin(table: Table<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.FULL, table, this)
    }

    fun where(clause: LogicExpress<Boolean>): QueryClause<T> {
        this.where = WhereClause(clause)
        return this
    }

    fun groupBy(vararg columns: Column<*>): QueryClause<T> {
        this.groupBy = GroupByClause(*columns)
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
        return queryer.executeQuery(express(), params().toList()).fetchAll(
            QuerySource(
                context = context,
                source = this.source!!,
            )
        )
    }

    private fun totalExpress(): Express {
        return ExpressMerge(select, from, where)
    }

    private fun merge(context: Context): ExpressMerge {
        val list = mutableListOf<Express?>(select, from)
        list.addAll(joins)
        list.addAll(arrayOf(where, groupBy, orderBy))
        if (limit != null) {
            list.add(limit)
            if (offset != null) {
                list.add(offset)
            }
        }
        val merge = ExpressMerge(*list.toTypedArray())
        merge.generate(context)
        return merge
    }

    override fun generate(context: Context) {
        computable.update(merge(context))
    }

    override fun express(): String {
        return queryClause.express()
    }

    override fun params(): Array<Any> {
        return queryClause.params()
    }

}