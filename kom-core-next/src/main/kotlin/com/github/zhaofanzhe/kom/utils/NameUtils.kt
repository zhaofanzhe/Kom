package com.github.zhaofanzhe.kom.utils

import com.github.zhaofanzhe.kom.KomException
import kotlin.reflect.KClass

object NameUtils {

    fun toColumnName(fieldName: String): String {
        val words = toWords(fieldName)
        return words.joinToString(separator = "_")
    }

    fun toTableName(entityName: String): String {
        val words = toWords(entityName)
        return words.joinToString(separator = "_")
    }

    fun toEntityName(kClass: KClass<*>): String {
        if (kClass.simpleName == "") {
            throw KomException("This class does not have simpleName, please check whether this class is anonymous")
        }
        return kClass.simpleName!!
    }

    private fun toWords(text: String): MutableList<String> {
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