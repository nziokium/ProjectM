package com.homey.projectm.features.onboarding.presentation.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.homey.projectm.features.onboarding.domain.model.SignUpData
import com.homey.projectm.features.onboarding.domain.useCases.OnboardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: OnboardingUseCases
) : ViewModel() {

    private val _userData = mutableStateOf(SignUpData())
    var userData: State<SignUpData> = _userData
    fun getUserData() {
        try {
            viewModelScope.launch {
                val userData = useCases.getUserData.execute()

                _userData.value = userData.data?: SignUpData()
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("Message", it) } // Log the actual exception message
        }
    }


    init {
        getUserData()
    }

    fun signOut(context: Context){
        viewModelScope.launch{
            useCases.signOut.execute(context = context)
        }

    }

}