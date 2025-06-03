package com.homey.projectm.features.onboarding.domain.model

data class SignUpCheck(
    var isSnackBarShown: Boolean = false,
    var errorMessage: String? = null
)