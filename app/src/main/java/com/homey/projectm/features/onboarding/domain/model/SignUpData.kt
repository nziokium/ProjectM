package com.homey.projectm.features.onboarding.domain.model

data class SignUpData(
    var email: String? = null,
    var password: String? = null,
    var uid: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var nationalId: String? = null
)