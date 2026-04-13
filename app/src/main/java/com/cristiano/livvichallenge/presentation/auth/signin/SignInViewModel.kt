package com.cristiano.livvichallenge.presentation.auth.signin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristiano.livvichallenge.core.error.ErrorMapper
import com.cristiano.livvichallenge.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            errorMessage = null
        )
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            errorMessage = null
        )
    }

    fun signIn() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(
                errorMessage = "Fill email and password"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(
                isLoading = true,
                errorMessage = null
            )

            authRepository.signIn(
                email = state.email.trim(),
                password = state.password
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    signInSuccess = true
                )
            }.onFailure { error ->
                val error = ErrorMapper.map(error)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = SignInUiState()
    }
}