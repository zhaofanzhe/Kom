package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.exception.KomException
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.starProjectedType

interface TypeConverter<T : Any, R : Any> {

    val from: KClass<T>

    val to: KClass<R>

    fun convert(from: T): R

    companion object {

        private val converters = mutableListOf<TypeConverter<*, *>>()

        fun <T : Any, R : Any> register(from: KClass<T>, to: KClass<R>, convert: (from: T) -> R) {
            converters += object : TypeConverter<T, R> {
                override val from: KClass<T>
                    get() = from
                override val to: KClass<R>
                    get() = to

                override fun convert(from: T): R {
                    return convert(from)
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T : Any, R : Any> convert(from: T, target: KClass<R>): R {
            if (from::class.starProjectedType.isSupertypeOf(target.starProjectedType)) {
                return from as R
            }
            val converter = converters.find {
                it.from.isInstance(from) && it.to.starProjectedType.isSubtypeOf(target.starProjectedType)
            } ?: throw KomException("未匹配到合适的转换器, from = ${from::class}, to = $target")
            return (converter as TypeConverter<T, R>).convert(from)
        }

        init {
            // 添加时间映射
            register(java.sql.Timestamp::class, java.time.LocalDateTime::class) { it.toLocalDateTime() }
        }

    }

}

