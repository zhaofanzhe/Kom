package com.github.zhaofanzhe.kom.generate

class ToolkitGenerate(private vararg val args: List<String>, private val generate: (List<String>) -> String) {

    private val counters: Array<Int> = args.map { 0 }.toTypedArray()

    private var eof = false

    private fun next(): List<String>? {

        if (eof) {
            return null
        }

        val list = mutableListOf<String>()

        args.forEachIndexed { index, arg ->
            list += arg[counters[index]]
        }

        var cursor = 0

        while (true) {
            if (cursor >= args.size) {
                eof = true
                break
            }
            counters[cursor]++
            if (counters[cursor] >= args[cursor].size) {
                counters[cursor] = 0
                cursor++
                continue
            }
            break
        }

        return list
    }

    fun exec(): String {
        val builder = StringBuilder()

        while (true) {
            val next = next() ?: break
            if (builder.isNotEmpty()) {
                builder.append("\n")
            }
            builder.append(generate(next))
        }

        return builder.toString()
    }

}