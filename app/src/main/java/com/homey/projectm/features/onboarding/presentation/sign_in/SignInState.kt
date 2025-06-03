package com.homey.projectm.features.onboarding.presentation.sign_in

// State to show whether or not sign in is successful,
// and to be used to navigate if it is.
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
