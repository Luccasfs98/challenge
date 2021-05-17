package com.picpay.core.data.base

import com.picpay.core.data.exceptions.*
import com.picpay.core.data.response.HttpExceptionErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

open class BaseRepository {
    private val _tag = this@BaseRepository::class.simpleName

    @Throws(
        BadRequestException::class,
        UnauthorizedException::class,
        NotFoundException::class,
        UnknownErrorException::class
    )
    fun parseErrorResponse(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> BadRequestException(
                        getErrorMessageFromErrorBody(
                            errorBody = throwable.response()?.errorBody()
                        )
                    )
                    401 -> UnauthorizedException(
                        getErrorMessageFromErrorBody(
                            errorBody = throwable.response()?.errorBody()
                        )
                    )
                    404 -> NotFoundException()
                    503 -> ServiceUnavailableException()
                    else -> UnknownErrorException()
                }
            }
            is ConnectException, is IOException -> InternetNotAvailableException(throwable.message)
            is EmptyDataException -> EmptyDataException()
            else -> UnknownErrorException()
        }
    }

    private fun getErrorMessageFromErrorBody(errorBody: ResponseBody?): String? {
        errorBody?.string().orEmpty().let {
            return Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
                .adapter(HttpExceptionErrorResponse::class.java)
                .fromJson(it)
                ?.messages
                ?.apiError
        }
    }
}