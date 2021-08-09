package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.functions.Function
import io.github.zhaofanzhe.kom.express.identifier.Identifier
import io.github.zhaofanzhe.kom.queryer.Queryer
import io.github.zhaofanzhe.kom.queryer.QuerySource
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
class QueryClause<T : Any>(
    private val queryer: Queryer,
) : Clause() {

    private var select: SelectClause? = null

    private var selectTable: ITable<*>? = null

    private var declares: List<Declare<*>> = listOf()

    private var from: FromClause? = null

    private var table: ITable<*>? = null

    private var joins: MutableList<JoinClause<*>> = mutableListOf()

    private var joinTables: MutableList<ITable<*>> = mutableListOf()

    private var where: WhereClause? = null

    private var groupBy: GroupByClause? = null

    private var orderBy: OrderByClause? = null

    private var limit: LimitClause? = null

    private var offset: OffsetClause? = null

    fun select(vararg declares: Declare<*>): QueryClause<Tuple> {
        this.select = SelectClause(declares.map { it.declare() })
        this.declares = declares.toList()
        this.selectTable = null
        return this as QueryClause<Tuple>
    }

    fun select(vararg tables: ITable<*>): QueryClause<Tuple> {
        val declares = mutableListOf<Declare<*>>()
        tables.forEach { declares += it.declares() }
        return select(*declares.toTypedArray())
    }

    @Suppress("UNCHECKED_CAST")
    fun <U : Any> select(table: ITable<U>): QueryClause<U> {
        val declares = table.declares()
        this.select = SelectClause(declares.map { it.declare() })
        this.declares = declares
        this.selectTable = table
        return this as QueryClause<U>
    }

    fun from(table: ITable<*>): QueryClause<T> {
        this.from = FromClause(table)
        this.table = table
        return this
    }

    internal fun join(clause: JoinClause<*>, table: ITable<*>): QueryClause<T> {
        joins.add(clause)
        joinTables.add(table)
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

    fun where(express: LogicExpress<Boolean>): QueryClause<T> {
        this.where = WhereClause(express)
        return this
    }

    fun groupBy(vararg columns: Column<T,*>): QueryClause<T> {
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

    fun subQuery(): SubQueryClause<T> {
        if (table == null) {
            throw KomException("""no have "from table"""")
        }
        val tables = mutableListOf<ITable<*>>()
        tables.add(table!!)
        tables.addAll(joinTables)
        return SubQueryClause(
            express = this,
            declares = this.declares,
            entityClass = selectTable?.entityClass as? KClass<T> ?: Tuple::class as KClass<T>,
            tables = tables,
        )
    }

    fun count(): Long {
        val table = subQuery()
        val count = table.count()
        val result = QueryClause<Void>(queryer)
            .select(count)
            .from(table).fetchOne() ?: return 0
        return result[count]?.toLong() ?: 0
    }

    fun fetchOne(): T? {
        val context = Context()
        val result = generate(context)
        val source = QuerySource(
            context = context,
            select = declares,
            table = selectTable,
        )
        return queryer.executeQuery(result.express(), result.params()).fetchOne(source)
    }

    fun fetchAll(): List<T> {
        val context = Context()
        val result = generate(context)
        val source = QuerySource(
            context = context,
            select = declares,
            table = selectTable,
        )
        return queryer.executeQuery(result.express(), result.params()).fetchAll(source)
    }

    override fun generate(context: Context, result: ExpressResult): IExpressResult {

        if (table == null) {
            throw KomException("""no have "from table"""")
        }

        val runtime = context.runtime

        val tables = mutableListOf<ITable<*>>()
        tables.add(table!!)
        tables.addAll(joinTables)

        val declares = mutableListOf<Declare<*>>()
        declares.addAll(table!!.declares())
        joinTables.forEach { declares.addAll(it.declares()) }

        context.runtime = Runtime(tables, declares)

        result += select?.generate(context)
        result += from?.generate(context)
        result += joins.map { it.generate(context) }
        result += where?.generate(context)
        result += groupBy?.generate(context)
        result += orderBy?.generate(context)
        if (limit != null) {
            result += limit?.generate(context)
            if (offset != null) {
                result += offset?.generate(context)
            }
        }

        if (runtime != null) {
            context.runtime = runtime
        }

        return result
    }

}