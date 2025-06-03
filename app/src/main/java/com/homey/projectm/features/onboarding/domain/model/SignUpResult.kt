package com.homey.projectm.features.onboarding.domain.model

import com.homey.projectm.features.onboarding.domain.model.SignUpData

data class SignUpResult(
    val data: SignUpData? = null,
    val errorMessage: String? = null
)
