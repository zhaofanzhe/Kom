package io.github.zhaofanzhe.kom.naming

import java.lang.StringBuilder

object Naming {

    fun toColumnName(fieldName: String): String {
        val words = toWords(fieldName)
        return words.joinToString(separator = "_")
    }

    fun toTableName(entityName: String): String {
        val words = toWords(entityName)
        return words.joinToString(separator = "_")
    }

    fun toEntityName(clazz: Class<*>): String {
        return clazz.typeName.split(".").last()
    }

    private fun toWords(text:String):MutableList<String>{
        val words = mutableListOf<String>()

        val word = StringBuilder()

        for (char in text) {
            if (char in 'A'..'Z') {
                if (word.isNotEmpty()) {
                    words += word.toString()
                    word.clear()
                }
                word.append((char + 32))
                continue
            }
            word.append(char)
        }

        if (word.isNotEmpty()) {
            words += word.toString()
        }

        return words
    }

}