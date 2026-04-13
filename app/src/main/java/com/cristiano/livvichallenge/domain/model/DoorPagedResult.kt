package com.cristiano.livvichallenge.domain.model

data class DoorPagedResult<T>(
    val items: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val isLastPage: Boolean
)