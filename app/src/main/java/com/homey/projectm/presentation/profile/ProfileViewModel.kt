package com.homey.projectm.presentation.profile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homey.projectm.presentation.sign_up.SignUpData
import kotlinx.coroutines.launch
import androidx.compose.runtime.State


class ProfileViewModel : ViewModel() {

    private val profileUiClient = ProfileUiClient()

    private val _userData = mutableStateOf(SignUpData())
    var userData: State<SignUpData> = _userData
    fun getUserData() {
        try {
            viewModelScope.launch {
                _userData.value = profileUiClient.getUserData()
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("Message", it) } // Log the actual exception message
        }
    }


    init {
        getUserData()
    }

}