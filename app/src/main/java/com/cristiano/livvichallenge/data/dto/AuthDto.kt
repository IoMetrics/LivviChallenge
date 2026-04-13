package com.cristiano.livvichallenge.data.dto

data class SignInRequestDto(
    val email: String,
    val password: String
)

data class SignInResponseDto(
    val token: String
)

data class SignUpRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class SignUpResponseDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String
)
