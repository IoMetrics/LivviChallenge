
package com.cristiano.livvichallenge.data.repository

import com.cristiano.livvichallenge.core.error.ErrorMapper
import com.cristiano.livvichallenge.core.network.ApiService
import com.cristiano.livvichallenge.core.storage.TokenStorage
import com.cristiano.livvichallenge.data.dto.SignInRequestDto
import com.cristiano.livvichallenge.data.dto.SignUpRequestDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage
) {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return runCatching {
            val response = apiService.signIn(
                SignInRequestDto(email, password)
            )
            tokenStorage.saveToken(response.token)
        }.recoverCatching { throwable ->
            throw Exception(
                ErrorMapper.map(
                    throwable = throwable,
                    defaultMessage = "Unable to sign in."
                ).message
            )
        }
    }

    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Unit> {
        return runCatching {
            apiService.signUp(
                SignUpRequestDto(firstName, lastName, email, password)
            )
            Unit
        }.recoverCatching { throwable ->
            throw Exception(
                ErrorMapper.map(
                    throwable = throwable,
                    defaultMessage = "Unable to create account."
                ).message
            )
        }
    }

    fun getSavedToken(): String? = tokenStorage.getToken()

    fun clearSession() {
        tokenStorage.clearToken()
    }
}


