package com.app.core.network

interface NetResponse<T> {
    fun result(): T?
    fun result(value: T?): NetResponse<T>
    fun code(): Int
    fun code(value: Int): NetResponse<T>
    fun message(): String?
    fun message(value: String?): NetResponse<T>
    fun success(): Boolean
    fun timeout(): Boolean
    fun failure(): Boolean
    fun unAuthorized(): Boolean
    fun lostConnection(): Boolean
}