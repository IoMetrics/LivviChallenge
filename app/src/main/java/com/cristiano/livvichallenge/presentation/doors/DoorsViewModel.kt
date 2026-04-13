package com.cristiano.livvichallenge.presentation.doors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cristiano.livvichallenge.core.error.ErrorMapper
import com.cristiano.livvichallenge.core.storage.TokenStorage
import com.cristiano.livvichallenge.data.mapper.toDomain
import com.cristiano.livvichallenge.data.repository.AuthRepository
import com.cristiano.livvichallenge.data.repository.DoorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DoorsViewModel @Inject constructor(
    private val doorsRepository: DoorsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoorsUiState())
    val uiState: StateFlow<DoorsUiState> = _uiState.asStateFlow()

    init {
        loadDoors(0)
    }

    fun onSearchQueryChanged(value: String) {
        _uiState.value = _uiState.value.copy(searchQuery = value)
    }

    fun loadDoors(page: Int = 0) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                doorsRepository.getDoors(page = page, size = 10)
            }.onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    doors = response.items,
                    currentPage = response.currentPage,
                    totalPages = response.totalPages,
                    isLastPage = response.isLastPage
                )
            }.onFailure { error ->
                val error = ErrorMapper.map(error)
                if (error.shouldLogout) {
                    authRepository.clearSession()
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message,
                    shouldLogout = error.shouldLogout
                )
            }
        }
    }

    fun searchDoors(page: Int = 0) {
        val query = _uiState.value.searchQuery.trim()

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                if (query.isBlank()) {
                    doorsRepository.getDoors(page = page, size = 10)
                } else {
                    doorsRepository.findDoors(
                        name = query,
                        page = page,
                        size = 10
                    )
                }
            }.onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    doors = response.items,
                    currentPage = response.currentPage,
                    totalPages = response.totalPages,
                    isLastPage = response.isLastPage
                )
            }.onFailure { error ->
                val error = ErrorMapper.map(error)
                if (error.shouldLogout) {
                    authRepository.clearSession()
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message,
                    shouldLogout = error.shouldLogout
                )
            }
        }
    }

    fun goToNextPage() {
        val nextPage = _uiState.value.currentPage + 1
        if (nextPage < _uiState.value.totalPages) {
            if (_uiState.value.searchQuery.isBlank()) {
                loadDoors(nextPage)
            } else {
                searchDoors(nextPage)
            }
        }
    }

    fun goToPreviousPage() {
        val previousPage = _uiState.value.currentPage - 1
        if (previousPage >= 0) {
            if (_uiState.value.searchQuery.isBlank()) {
                loadDoors(previousPage)
            } else {
                searchDoors(previousPage)
            }
        }
    }


    fun logout() {
        authRepository.clearSession()
    }
}

