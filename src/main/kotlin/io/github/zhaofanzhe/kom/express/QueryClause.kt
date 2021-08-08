package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress
import io.github.zhaofanzhe.kom.queryer.QuerySource
import io.github.zhaofanzhe.kom.queryer.Queryer
import io.github.zhaofanzhe.kom.tool.Computable

@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class QueryClause<T : Any>(private val queryer: Queryer) : Clause() {

    private val computable = Computable {
        merge(context)
    }

    private var context: Context by computable.observable(Context())

    private var select: SelectClause? by computable.observable(null)

    private var from: FromClause? by computable.observable(null)

    private var where: WhereClause? by computable.observable(null)

    private var groupBy: GroupByClause? by computable.observable(null)

    private var orderBy: OrderByClause? by computable.observable(null)

    private var limit: LimitClause? by computable.observable(null)

    private var offset: OffsetClause? by computable.observable(null)

    private var joins: MutableList<JoinClause<*>> by computable.observable(mutableListOf())

    private val queryClause: Express by computable

    var source: Any? = null
        private set

    var declares: List<Declare<*>> = emptyList()
        private set

    fun <U : Any> select(table: ITable<U>): QueryClause<U> {
        this.declares = table.declares()
        this.select = SelectClause(*table.declareExpress())
        this.source = table.source()
        return this as QueryClause<U>
    }

    fun select(vararg tables: ITable<*>): QueryClause<Tuple> {
        val declares = mutableListOf<Declare<*>>()
        val declareExpresses = mutableListOf<DeclareExpress>()
        tables.forEach {
            declares.addAll(it.declares())
            declareExpresses.addAll(it.declareExpress())
        }
        this.declares = declares
        this.select = SelectClause(*declareExpresses.toTypedArray())
        this.source = Tuple::class
        return this as QueryClause<Tuple>
    }

    fun select(vararg declares: Declare<*>): QueryClause<Tuple> {
        this.declares = declares.toList()
        this.select = SelectClause(*declares.map { it.declareExpress() }.toTypedArray())
        this.source = Tuple::class
        return this as QueryClause<Tuple>
    }

    fun from(table: ITable<*>): QueryClause<T> {
        this.from = FromClause(table)
        return this
    }

    internal fun join(clause: JoinClause<*>): QueryClause<T> {
        joins.add(clause)
        return this
    }

    fun innerJoin(table: ITable<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.INNER, table, this)
    }

    fun leftJoin(table: ITable<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.LEFT, table, this)
    }

    fun rightJoin(table: ITable<*>): JoinClause<T> {
        return JoinClause(JoinClause.Genre.RIGHT, table, this)
    }

    fun fullJoin(table: ITable<*>): JoinClause<T> {
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

    fun subQuery(): SubQuery<T> {
        return SubQuery(clone())
    }

    fun fetchOne(): T? {
        return queryer.executeQuery(express(), params().toList()).fetchOne(
            QuerySource(
                context = context,
                select = declares.toTypedArray(),
                source = this.source!!,
            )
        )
    }

    fun fetchAll(): List<T> {
        return queryer.executeQuery(express(), params().toList()).fetchAll(
            QuerySource(
                context = context,
                select = declares.toTypedArray(),
                source = this.source!!,
            )
        )
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
        this.context = context
    }

    override fun express(): String {
        return queryClause.express()
    }

    override fun params(): Array<Any> {
        return queryClause.params()
    }

    fun clone(): QueryClause<T> {
        val clause = QueryClause<T>(queryer = queryer)
        clause.select = this.select
        clause.from = this.from
        clause.where = this.where
        clause.groupBy = this.groupBy
        clause.orderBy = this.orderBy
        clause.limit = this.limit
        clause.offset = this.offset
        clause.joins = this.joins
        clause.source = this.source
        clause.declares = this.declares
        return clause
    }

}