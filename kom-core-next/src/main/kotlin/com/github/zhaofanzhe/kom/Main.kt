package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.*
import com.github.zhaofanzhe.kom.dsl.express.desc
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.gt
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.statement.from
import com.github.zhaofanzhe.kom.dsl.statement.orderBy
import com.github.zhaofanzhe.kom.dsl.statement.where

class Accounts : Table("account") {
    val id = int("id").primaryKey().autoIncrement()
    val phone = varchar("phone").index()
}

fun main() {

    val database = Database()

    val accounts = Accounts()

    val statement = database.select(accounts.id, accounts.phone)
        .from(accounts)
        .where(useOr {
            use(accounts.id gt 1.param)
            use(accounts.phone eq "121212".param)
            useAnd {
                use(accounts.id gt 1.param)
                use(accounts.phone eq "121212".param)
            }
        })
        .orderBy(accounts.id.desc)

    val bundle = statement.generateStatement()

    println(bundle.sql)
    println(bundle.args)

}