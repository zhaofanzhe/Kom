package io.github.zhaofanzhe.kom.express

abstract class Express : IExpress {

    override fun generate(context: Context): IExpressResult {
        return generate(context, ExpressResult())
    }

    abstract fun generate(context: Context, result: ExpressResult): IExpressResult

    override fun toString(): String {
        return generate(Context()).express()
    }

}