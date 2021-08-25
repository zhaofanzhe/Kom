package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.Express

/**
 * 声明表达式
 *  - select [user.id as user_0__id]
 *  - select [user.username as user_0__username]
 *  - select [count(*) as __col_0]
 */
abstract class DeclareExpress : Express()