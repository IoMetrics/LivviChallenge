package com.cristiano.livvichallenge.core.error

import retrofit2.HttpException

fun Throwable.isUnauthorized(): Boolean {
    return this is HttpException && this.code() == 401
}