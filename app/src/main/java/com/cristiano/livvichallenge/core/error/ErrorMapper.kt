
package com.cristiano.livvichallenge.core.error

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class ErrorResult(
    val message: String,
    val shouldLogout: Boolean = false
)

object ErrorMapper {

    fun map(
        throwable: Throwable,
        defaultMessage: String = "Something went wrong."
    ): ErrorResult {
        return when (throwable) {
            is HttpException -> mapHttp(throwable, defaultMessage)

            is UnknownHostException -> ErrorResult(
                message = "We couldn’t reach the server. Please check your internet connection and try again."
            )

            is SocketTimeoutException -> ErrorResult(
                message = "The request took too long. Please try again."
            )

            is IOException -> ErrorResult(
                message = "A network error occurred. Please try again."
            )

            else -> ErrorResult(
                message = throwable.message ?: defaultMessage
            )
        }
    }

    private fun mapHttp(
        exception: HttpException,
        defaultMessage: String
    ): ErrorResult {
        val shouldLogout = exception.code() == 401

        return try {
            val raw = exception.response()?.errorBody()?.string().orEmpty()

            if (raw.isBlank()) {
                return ErrorResult(
                    message = defaultHttpMessage(exception.code(), defaultMessage),
                    shouldLogout = shouldLogout
                )
            }

            val json = JSONObject(raw)
            val description = json.optString("description")
            val fieldErrors = json.optJSONArray("fieldErrors")

            val firstFieldMessage =
                if (fieldErrors != null && fieldErrors.length() > 0) {
                    fieldErrors.getJSONObject(0).optString("message")
                } else {
                    ""
                }

            val message = when {
                firstFieldMessage.isNotBlank() -> translateFieldMessage(firstFieldMessage)
                description.isNotBlank() -> description
                else -> defaultHttpMessage(exception.code(), defaultMessage)
            }

            ErrorResult(
                message = message,
                shouldLogout = shouldLogout
            )
        } catch (_: Exception) {
            ErrorResult(
                message = defaultHttpMessage(exception.code(), defaultMessage),
                shouldLogout = shouldLogout
            )
        }
    }

    private fun defaultHttpMessage(
        code: Int,
        defaultMessage: String
    ): String {
        return when (code) {
            400 -> "The request is invalid."
            401 -> "Your session has expired. Please sign in again."
            403 -> "You do not have permission to perform this action."
            404 -> "The requested resource was not found."
            500, 502, 503 -> "The server is currently unavailable. Please try again later."
            else -> defaultMessage
        }
    }

    private fun translateFieldMessage(message: String): String {
        return when (message) {
            "Password must be 4–12 characters and contain at least one uppercase letter, one number, and one symbol" ->
                "Password must be 4 to 12 characters long and include at least one uppercase letter, one number, and one symbol."
            else -> message
        }
    }
}


