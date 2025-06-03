package com.homey.projectm.features.onboarding.presentation.sign_up

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignUpCheck
import com.homey.projectm.features.onboarding.domain.model.UIEvent
import com.homey.projectm.features.onboarding.domain.useCases.OnboardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: OnboardingUseCases
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var userName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var idNumber by mutableStateOf("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiEventChannel = Channel<UIEvent>()
    val uiEvent = _uiEventChannel.receiveAsFlow()

    fun signUpWithEmailAndPassword(): SignUpCheck {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val data = useCases.signUpWithEmailAndPassword.execute(email,password)
                data
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
                SignUpCheck()
            } finally {
                _isLoading.value = false
            }
        }
        return SignUpCheck()
    }


    fun updateUserDetails() {
        try {
            viewModelScope.launch {
                useCases.updateUserDetails.execute(phoneNumber, idNumber, userName)
            }
        } catch (e: Exception) {
            Log.d("Error", "Update failed.")
        }
    }

    fun signUpWithGoogle(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.signInWithGoogle.execute(context).collect { signInResult ->
                when(signInResult){
                    is Resource.Error -> {
                        _uiEventChannel.send(UIEvent.ShowSnackBar("Error: ${signInResult.message}"))
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                }
            }
        }
    }
}

