package com.picpay.core.data.response

data class HttpExceptionErrorResponse(
        val code: Int?,
        val messages: Message?)

data class Message(
        val apiError: String?
)