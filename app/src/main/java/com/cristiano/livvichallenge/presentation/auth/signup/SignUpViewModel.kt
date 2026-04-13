package com.cristiano.livvichallenge.presentation.auth.signup

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()


    fun onFirstNameChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            firstName = value,
            firstNameError = null,
            errorMessage = null
        )
    }


    fun onLastNameChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            lastName = value,
            lastNameError = null,
            errorMessage = null
        )
    }

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = null,
            errorMessage = null
        )
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            passwordError = null,
            errorMessage = null)
    }




    fun signUp() {
        val state = _uiState.value

        val firstNameError = if (state.firstName.isBlank()) "Please enter your first name" else null
        val lastNameError = if (state.lastName.isBlank()) "Please enter your last name" else null
        val emailError = when {
            state.email.isBlank() -> "Please enter your email"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches() ->
                "Invalid email address"
            else -> null
        }
        val passwordError = when {
            state.password.isBlank() -> "Please enter your password"
            state.password.length < 4 -> "Password is too short"
            else -> null
        }

        if (
            firstNameError != null ||
            lastNameError != null ||
            emailError != null ||
            passwordError != null
        ) {
            _uiState.value = state.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
                passwordError = passwordError,
                errorMessage = "Please review the highlighted fields."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(
                isLoading = true,
                errorMessage = null,
                firstNameError = null,
                lastNameError = null,
                emailError = null,
                passwordError = null
            )

            authRepository.signUp(
                firstName = state.firstName.trim(),
                lastName = state.lastName.trim(),
                email = state.email.trim(),
                password = state.password
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    signUpSuccess = true
                )
            }.onFailure { error ->
                val message =  ErrorMapper.map(error)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = message.message,
                    emailError = if (message.message.contains("email is already registered", ignoreCase = true)) {
                        "Email already registered"
                    } else null
                )
            }
        }
    }
}