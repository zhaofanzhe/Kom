# Kom - 一个 kotlin 的 ORM 框架

中文 | [English (Google translation)](./README.en.md)

## 说明
目前的进度只是一个玩具,有兴趣的人可以加入进来

不定时更新

有任何问题可以通过 [Issues](https://github.com/zhaofanzhe/Kom/issues) 联系我

### 例子
```kotlin
package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.toolkit.and
import io.github.zhaofanzhe.kom.toolkit.eq
import io.github.zhaofanzhe.kom.toolkit.gt
import io.github.zhaofanzhe.kom.toolkit.lt
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
) {
    constructor() : this(id = 0, username = "")
}

class Users : Table<User>(User::class) {
    val id = column(User::id)
    val username = column(User::username)
}

data class Address(
    var id: Int = 0,
    var address: String = "",
    var userId: Int = 0,
) {
    constructor() : this(id = 0, address = "", userId = 0)
}

class Addresses : Table<Address>(Address::class) {
    var id = column(Address::id)
    var address = column(Address::address)
    var userId = column(Address::userId)
}

fun main() {

    val database = getDatabase()

    val users = Users()
    val address = Addresses()

    val t1 = database.selectFrom(users)
        .where(and {
            and(users.id gt 1)
        }).subQuery()

    val express = database.select(users,address)
        .from(t1)
        .leftJoin(address)
        .on(and {
            and(users.id eq address.userId)
        })

    println(express)

    val list = express.fetchAll()

    println(list)

    list.forEach {
        println(it[users])
        println(it[address])
    }

}

fun getDatabase(): Database {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "123456")
    return Database(object : ConnectionFactory {
        override fun getConnection(): Connection {
            return connection
        }
    })
}
```