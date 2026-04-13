package com.cristiano.livvichallenge.presentation.doors

import com.cristiano.livvichallenge.domain.model.Door

data class DoorsUiState(
    val isLoading: Boolean = false,
    val doors: List<Door> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null,
    val shouldLogout: Boolean = false,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val isLastPage: Boolean = false
)