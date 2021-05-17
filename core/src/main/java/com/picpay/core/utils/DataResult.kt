package com.picpay.core.utils

sealed class DataResult<out T : Any>

class Loading<T : Any>() : DataResult<T>()
class Success<T : Any>(var data: T) : DataResult<T>()
class Error(val exception: Throwable? = null, val message: String = "") : DataResult<Nothing>()