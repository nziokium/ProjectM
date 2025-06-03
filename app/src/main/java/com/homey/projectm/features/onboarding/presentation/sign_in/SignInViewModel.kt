package com.homey.projectm.features.onboarding.presentation.sign_in

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignInResult
import com.homey.projectm.features.onboarding.domain.model.UIEvent
import com.homey.projectm.features.onboarding.domain.useCases.OnboardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: OnboardingUseCases
): ViewModel(){


    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _uiEventChannel = Channel<UIEvent>()
    val uiEvent = _uiEventChannel.receiveAsFlow()

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

    private fun resetState(){
        _state.update{ SignInState() }
    }

    fun onSignInRequest(){
        try {
            viewModelScope.launch {
                useCases.signInWithEmailAndPassword.execute(email, password)
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

    fun signInWithGoogle(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.signInWithGoogle.execute(context).collect { signInResult ->
                when(signInResult){
                    is Resource.Error -> {
                        _uiEventChannel.send(UIEvent.ShowSnackBar("Error: ${signInResult.message}"))
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        resetState()
                        _uiEventChannel.send(UIEvent.ChangeScreens)
                    }
                }
            }
        }
    }
}