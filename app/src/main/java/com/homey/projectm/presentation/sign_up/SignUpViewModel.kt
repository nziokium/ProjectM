package com.homey.projectm.presentation.sign_up

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.homey.projectm.presentation.sign_in.SignInResult
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

class SignUpViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var userName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var idNumber by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    var isSnackbarShown by mutableStateOf(false)
    private val passwordAuthUiClient = PasswordAuthUiClient()

    suspend fun signUpWithEmailAndPassword(): SignUpCheck {

        return try {
            isLoading
            // Use async to perform the asynchronous operation
            val data = passwordAuthUiClient.signUpUserWithEmailAndPassword(email, password)
            !isLoading
            // Set isSnackbarShown based on the fetched data
            data
        } catch (e: Exception) {
            Log.d("Error", "Issue in the Sign Up ViewModel")
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignUpCheck()
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

