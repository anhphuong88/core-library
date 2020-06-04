package com.app.core.network

internal class NetResponseImpl<T> : NetResponse<T> {
    var result: T? = null
    var code: Int? = null
    var message: String? = null

    override fun success(): Boolean = 200 == code

    override fun timeout(): Boolean = -100 == code

    override fun unAuthorized(): Boolean = 401 == code

    override fun failure(): Boolean = -101 == code

    override fun lostConnection(): Boolean = -102 == code

    override fun result(): T? = result

    override fun code(): Int = code ?: 0

    override fun message(): String? = message

    override fun result(value: T?): NetResponse<T> {
        result = value
        return this
    }

    override fun code(value: Int): NetResponse<T>  {
        code = value
        return this
    }

    override fun message(value: String?): NetResponse<T>  {
        message = value
        return this
    }
    
}