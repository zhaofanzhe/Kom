package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareStatementExpress
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class Column<U : Any, T : Any?>(
    val clazz: KClass<*>,
    override val name: String,
    val fieldName: String,
    val nullable: Boolean,
    override val table: ITable<*>,
    override val ref: Column<U, T>? = null,
) : Declare<T> {

    internal var primaryKey: Boolean = false
        private set

    internal var autoIncrement: Boolean = false
        private set

    internal var unique: Boolean = false
        private set

    internal var uniqueKey: String? = null
        private set

    override fun declare(): DeclareExpress {
        return DeclareStatementExpress(this)
    }

    override fun express(): Express {
        return DeclareRefExpress(this)
    }

    override fun newTableDeclare(table: ITable<*>): Declare<T> {
        return Column(
            clazz = this.clazz,
            name = this.name,
            fieldName = this.fieldName,
            nullable = this.nullable,
            table = table,
            ref = this,
        )
    }

    fun primaryKey(): Column<U, T> {
        this.primaryKey = true
        return this
    }

    fun autoIncrement(): Column<U, T> {
        this.autoIncrement = true
        return this
    }

    fun unique(uniqueKey: String? = null): Column<U, T> {
        this.unique = true
        this.uniqueKey = uniqueKey
        return this
    }

    override fun toString(): String {
        return name
    }

}