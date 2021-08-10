package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.queryer.Queryer

@Suppress("UNCHECKED_CAST")
class InsertClause<T : Any>(
    private val queryer: Queryer,
    private val table: Table<T>,
) : Clause() {

    /**
     * 0 : 未初始化
     * 1 : insert user(username) value (?);
     * 2 : insert user(username) select * from;
     */
    private var mode: Int = 0
        set(value) {
            if (field != 0 && field != value) {
                throw KomException("insert mode cannot be mixed")
            }
            field = value
        }

    private var mode1Columns = mutableMapOf<Column<T, *>, Any?>()

    private var mode2columns: List<Column<T, *>>? = null

    private var mode2SubQueryClause: SubQueryClause<*>? = null

    fun <U : Any?> set(column: Column<T, U>, value: U): InsertClause<T> {
        this.mode = 1
        mode1Columns[column] = value
        return this
    }

    fun columns(vararg column: Column<T, *>): InsertClause<T> {
        this.mode = 2
        this.mode2columns = column.toList()
        return this
    }

    fun columns(table: Table<T>): InsertClause<T> {
        return columns(*table.declares().map { it as Column<T, *> }.toTypedArray())
    }

    fun select(query: SubQueryClause<*>): InsertClause<T> {
        this.mode = 2
        this.mode2SubQueryClause = query
        return this
    }

    fun execute(): Boolean {
        val result = ExpressResult()
       generate(Context(),result)
        return queryer.execute(result.express(), result.params()) == 1
    }

    override fun generate(context: Context, result: ExpressResult) {

        when (mode) {
            0 -> throw KomException("mode un init")
            1 -> {
                if (this.mode1Columns.isEmpty()) throw KomException("not set column.")
            }
            2 -> {
                if (this.mode2columns == null) throw KomException("no call columns().")
                if (this.mode2SubQueryClause == null) throw KomException("no call select().")
            }
        }

        result += "insert into "
        result += table.tableName

        if (mode == 1) {
            result += "("
            val keys = mode1Columns.keys
            keys.forEachIndexed { index, column ->
                if (index > 0) {
                    result += ", "
                }
                result += column.name
            }
            result += ") values ("
            keys.forEachIndexed { index, column ->
                if (index > 0) {
                    result += ", "
                }
                result.append("?", mode1Columns[column])
            }
            result += ")"
        } else {
            result += "("
            mode2columns?.forEachIndexed { index, column ->
                if (index > 0) {
                    result += ", "
                }
                result += column.name
            }
            result += ") \n"
            mode2SubQueryClause?.generateUnenclosed(context, result)
        }
    }

}