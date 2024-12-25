package com.github.zhaofanzhe.kom.expression

interface SqlExpression {

    fun generate()

}

public abstract class QuerySourceExpression : SqlExpression

public sealed class QueryExpression : QuerySourceExpression() {
//    public abstract val orderBy: List<OrderByExpression>
//    public abstract val offset: Int?
//    public abstract val limit: Int?
//    public abstract val tableAlias: String?
}

public sealed class SelectExpression : QueryExpression() {

}

public sealed class UnionExpression : QueryExpression() {}

//interface User {
//
//    var id by int()
//
//    var name by varchar()
//
//    var age by int()
//
//    override val primaryKey = [User::id]
//
//}