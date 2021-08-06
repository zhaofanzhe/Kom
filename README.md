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
import io.github.zhaofanzhe.kom.express.Entity
import io.github.zhaofanzhe.kom.toolkit.and
import io.github.zhaofanzhe.kom.toolkit.desc
import io.github.zhaofanzhe.kom.toolkit.eq
import io.github.zhaofanzhe.kom.toolkit.neq
import java.sql.Connection
import java.sql.DriverManager

data class User(
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
)

class Users : Entity<User>(User::class) {
    val id = field(User::id)
    val username = field(User::username)
    val password = field(User::password)
}

fun main() {

    val database = getDatabase()

    val users = Users()

    val express = database.select(users.id, users.username)
        .from(users)
        .where(and {
            and(users.username.neq("张三"))
            or {
                or(users.id.eq(1))
                or(users.id.eq(2))
            }
        })
        .groupBy(users.id)
        .orderBy(users.id.desc())
        .limit(10)
        .offset(0)

    println(express)

    val list = express.fetchAll()

    println(list)

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