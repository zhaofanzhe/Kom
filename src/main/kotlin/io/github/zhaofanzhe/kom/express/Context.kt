package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.tool.ColumnAliasGenerator
import io.github.zhaofanzhe.kom.tool.TableAliasGenerator

@Suppress("MemberVisibilityCanBePrivate")
class Context {

    internal val tableAliasGenerator = TableAliasGenerator()

    internal val columnAliasGenerator = ColumnAliasGenerator(tableAliasGenerator)

}