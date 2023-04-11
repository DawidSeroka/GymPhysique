package com.myproject.gymphysique.core.common

class Result<T> private constructor(
    private val value: T?,
    private val exception: Exception?,
    private val isLoading: Boolean = false
) {
    companion object {
        fun <T> success(data: T): Result<T> = Result(data, null,false)

        fun <T> error(e: Exception): Result<T> = Result(null, e,false)

        fun <T> loading(data: T): Result<T> = Result(data, null, true)
    }

    fun isSuccess(): Boolean = value != null && !isLoading

    fun isError(): Boolean = exception != null

    fun isLoading(): Boolean = isLoading

    fun value(): T {
        if (isSuccess()) return value!!
        else throw IllegalArgumentException("Data is null!")
    }

    fun cause(): Exception {
        if (isError()) return exception!!
        else throw IllegalArgumentException("Exception is null!")
    }
}