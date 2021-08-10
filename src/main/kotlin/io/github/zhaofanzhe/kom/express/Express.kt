package io.github.zhaofanzhe.kom.express

abstract class Express : IExpress {

    override fun toString(): String {
        val result = ExpressResult()
        generate(Context(), result)
        return result.express()
    }

}