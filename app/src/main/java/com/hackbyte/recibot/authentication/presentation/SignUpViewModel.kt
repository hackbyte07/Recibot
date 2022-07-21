package com.hackbyte.recibot.authentication.presentation

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackbyte.recibot.authentication.domain.AuthRepository
import com.hackbyte.recibot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("Unknown error")
        private set

    var isSignInSuccess by mutableStateOf(false)
        private set

    var emailError by mutableStateOf(false)
        private set

    var passwordError by mutableStateOf(false)
        private set

    fun resetEmailError() {
        emailError = false
    }

    fun resetPasswordError() {
        passwordError = false
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        val inputEmail = email.trim()
        val inputPassword = password.trim()
        if (inputEmail.isEmpty() || inputEmail.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(
                inputEmail
            ).matches()
        ) {
            emailError = true
            return
        }
        if (inputPassword.isEmpty() || inputPassword.isBlank() || inputPassword.length < 6) {
            passwordError = true
            return
        }
        viewModelScope.async(Dispatchers.IO) {
            authRepository.signUpWithEmailAndPassword(email, password).collect {
                when (it) {
                    is Resource.Loading -> loading = it.loading
                    is Resource.Success -> {
                        if (it.data == true)
                            isSignInSuccess = true
                    }
                    is Resource.Error -> errorMessage = it.throwable?.message ?: "Unknown"
                }
            }
        }
    }


}