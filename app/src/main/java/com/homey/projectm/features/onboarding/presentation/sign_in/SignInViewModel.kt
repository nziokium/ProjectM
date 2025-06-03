package com.homey.projectm.features.onboarding.presentation.sign_in

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homey.projectm.features.onboarding.domain.model.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class SignInViewModel: ViewModel(){

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val signInWithPassUiClient = SignInWithPassUiClient()

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var isSignInComplete by mutableStateOf(false)

    fun onSignInResult(result: SignInResult){
        _state.update{ it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )

        }
    }

    fun resetState(){
        _state.update{ SignInState() }
    }

    fun onSignInRequest(){
        try {
            viewModelScope.launch {
                signInWithPassUiClient.signInUserWithEmailAndPassword(email, password)
                Log.d("Success",
                    "The email $email and the password $password have been updated to firebase")
                isSignInComplete
            }
        }catch (e: Exception){
            Log.d("Error", "Issue in the Sign Up ViewModel")
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }
}