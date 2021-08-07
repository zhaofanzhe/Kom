package io.github.zhaofanzhe.kom.tool

class AliasGenerator {

    private val generators = mutableMapOf<String, SequenceGenerator>()

    fun next(name: String): String {
        val generator = generators.getOrPut(name) { SequenceGenerator() }
        return """${name}_${generator.next()}"""
    }

}