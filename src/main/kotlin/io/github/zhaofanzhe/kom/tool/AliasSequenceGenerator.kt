package io.github.zhaofanzhe.kom.tool

class AliasSequenceGenerator {

    private val generators = mutableMapOf<String, SequenceGenerator>()

    fun next(name: String): String {
        var generator = generators[name]
        if (generator == null){
            generator= SequenceGenerator()
            generators[name] = generator
        }
        return """${name}_${generator.next()}"""
    }

}