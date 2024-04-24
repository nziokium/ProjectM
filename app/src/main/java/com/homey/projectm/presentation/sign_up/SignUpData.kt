package com.homey.projectm.presentation.sign_up

import androidx.compose.runtime.MutableState

data class SignUpResult(
    val data: SignUpData? = null,
    val errorMessage: String? = null
)
data class SignUpData(
    var email: String? = null,
    var password: String? = null,
    var uid: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var nationalId: String? = null
)

