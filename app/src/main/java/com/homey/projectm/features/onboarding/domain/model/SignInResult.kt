package com.homey.projectm.features.onboarding.domain.model

//This is a data class containing the user data and error if something goes wrong
data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
)