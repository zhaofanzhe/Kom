package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.core.fill
import com.github.zhaofanzhe.kom.dsl.column.*
import com.github.zhaofanzhe.kom.dsl.entity.Entity
import com.github.zhaofanzhe.kom.dsl.entity.find
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.statement.dml.fetchOne
import com.github.zhaofanzhe.kom.dsl.statement.dml.where
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.table.datetime
import com.github.zhaofanzhe.kom.dsl.table.int
import com.github.zhaofanzhe.kom.dsl.table.varchar
import com.github.zhaofanzhe.kom.toolkit.KomToolkit
import com.github.zhaofanzhe.kom.toolkit.migrate
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime
import kotlin.reflect.KClass

open class BaseEntity<T : Table>(
    clazz: KClass<T>,
) : Entity<T>(clazz) {

    var createdAt: LocalDateTime? = null

    var updateAt: LocalDateTime? = null

    var deleteAt: LocalDateTime? = null

}

class User : BaseEntity<Users>(Users::class) {

    var id: Int = 0

    var username: String = ""

    var password: String = ""
    override fun toString(): String {
        return "User(createdAt=$createdAt, updateAt=$updateAt, deleteAt=$deleteAt, id=$id, username='$username', password='$password')"
    }

}

open class BaseTable(name: String) : Table(name) {
    val createdAt = datetime("created_at").index().comment("创建时间").default { LocalDateTime.now() }
    val updateAt = datetime("update_at").index().comment("更新时间").update(true) { LocalDateTime.now() }
    val deleteAt = datetime("delete_at").nullable().index().comment("删除时间")
}

class Users : BaseTable("users") {
    val id = int("id").primaryKey().autoIncrement().comment("编号")
    val username = varchar("username").comment("用户名").index()
}

fun getMySQLConnectionFactory(): ConnectionFactory {
    return object : ConnectionFactory {
        override fun getConnection(): Connection {
            return DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/demo", "root", "123456")
        }
    }
}

fun main() {

    val database = Database(getMySQLConnectionFactory())

    val users = Users()

    KomToolkit.migrate(database, users)

    val user = database.selectFrom(users)
        .where { users.id eq 1.param }
        .fetchOne()?.fill<User>() ?: return

    println(user)

}