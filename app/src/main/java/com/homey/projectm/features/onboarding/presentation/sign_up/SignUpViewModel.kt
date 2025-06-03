package com.homey.projectm.features.onboarding.presentation.sign_up

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import com.homey.projectm.features.onboarding.domain.model.SignUpCheck
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

import kotlin.coroutines.cancellation.CancellationException

class SignUpViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var userName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var idNumber by mutableStateOf("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val passwordAuthUiClient = PasswordAuthUiClient()

    fun signUpWithEmailAndPassword(): SignUpCheck {
        viewModelScope.launch {
            return@launch try {
                _isLoading.value = true
                // Perform your asynchronous operation here
                val data = passwordAuthUiClient.signUpUserWithEmailAndPassword(email, password)

                data

            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
                SignUpCheck()
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateUserDetails() {
        try {
            viewModelScope.launch {
                passwordAuthUiClient.updateUserDetails(phoneNumber, idNumber, userName)
            }
        } catch (e: Exception) {
            Log.d("Error", "Update failed.")
        }
    }
}

