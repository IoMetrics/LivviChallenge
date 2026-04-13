package com.cristiano.livvichallenge.presentation.doors.events


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristiano.livvichallenge.core.error.ErrorMapper
import com.cristiano.livvichallenge.data.repository.AuthRepository
import com.cristiano.livvichallenge.data.repository.DoorEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DoorEventsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DoorEventsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val doorId: Int = checkNotNull(savedStateHandle["doorId"])

    private val _uiState = MutableStateFlow(DoorEventsUiState())
    val uiState: StateFlow<DoorEventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }


    fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )


            runCatching {
                repository.getDoorEvents(doorId = doorId)
            }.onSuccess { events ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    events = events
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
}