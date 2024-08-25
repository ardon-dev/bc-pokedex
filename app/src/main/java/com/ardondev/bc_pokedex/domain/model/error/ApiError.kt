package com.ardondev.bc_pokedex.domain.model.error

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val BAD_REQUEST_ERROR_MESSAGE = "Bad Request!"
const val FORBIDDEN_ERROR_MESSAGE = "Forbidden!"
const val NOT_FOUND_ERROR_MESSAGE = "404 Not Found!"
const val METHOD_NOT_ALLOWED_ERROR_MESSAGE = "Method Not Allowed!"
const val CONFLICT_ERROR_MESSAGE = "Conflict!"
const val UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized!"
const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server error!"
const val NO_CONNECTION_ERROR_MESSAGE = "No Connection!"
const val TIMEOUT_ERROR_MESSAGE = "Time Out!"
const val NO_CONTENT_ERROR_MESSAGE = "No content!"
const val UNKNOWN_ERROR_MESSAGE = "Unknown Error!"
const val UNKNOWN_HOST_ERROR_MESSAGE = "Unknown Host!"

fun traceErrorException(throwable: Throwable?): Throwable {

    return when (throwable) {

        is HttpException -> {
            when (throwable.code()) {
                204 -> Throwable(
                    NO_CONTENT_ERROR_MESSAGE,
                    throwable
                )

                400 -> Throwable(
                    BAD_REQUEST_ERROR_MESSAGE,
                    throwable
                )

                401 -> Throwable(
                    UNAUTHORIZED_ERROR_MESSAGE,
                    throwable
                )

                403 -> Throwable(
                    FORBIDDEN_ERROR_MESSAGE,
                    throwable
                )

                404 -> Throwable(
                    NOT_FOUND_ERROR_MESSAGE,
                    throwable
                )

                405 -> Throwable(
                    METHOD_NOT_ALLOWED_ERROR_MESSAGE,
                    throwable
                )

                409 -> Throwable(
                    CONFLICT_ERROR_MESSAGE,
                    throwable
                )

                422 -> Throwable(
                    BAD_REQUEST_ERROR_MESSAGE,
                    throwable
                )

                500 -> Throwable(
                    INTERNAL_SERVER_ERROR_MESSAGE,
                    throwable
                )

                else -> Throwable(
                    UNKNOWN_ERROR_MESSAGE,
                    throwable
                )
            }
        }

        is UnknownHostException -> {
            Throwable(UNKNOWN_HOST_ERROR_MESSAGE, throwable)
        }

        is SocketTimeoutException -> {
            Throwable(TIMEOUT_ERROR_MESSAGE, throwable)
        }

        is IOException -> {
            Throwable(NO_CONNECTION_ERROR_MESSAGE, throwable)
        }

        else -> Throwable(UNKNOWN_ERROR_MESSAGE, throwable)

    }
}
