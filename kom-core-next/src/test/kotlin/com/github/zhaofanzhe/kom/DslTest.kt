package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.dsl.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DslTest {

    private val database = Database()

    @Test
    fun testQuery() {
        val sql = database.from(Users).drop(10).take(10).sql
        assertEquals("SELECT * FROM users LIMIT ? OFFSET ?", sql)
    }

    @Test
    fun testUnion() {
        val sql = database.from(Users)
            .where { Users.id eq 1 }
            .union {
                database.from(Users).where { Users.id eq 2 }
            }
            .unionAll {
                database.from(Users).where { Users.id eq 3 }
            }
            .drop(10)
            .take(10)
            .sql
        assertEquals(
            "SELECT * FROM users WHERE id = ? UNION SELECT * FROM users WHERE id = ? UNION ALL SELECT * FROM users WHERE id = ? LIMIT ? OFFSET ?",
            sql
        )
    }

}