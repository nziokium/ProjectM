package com.homey.projectm.features.onboarding.domain.repostiory

import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignUpCheck
import com.homey.projectm.features.onboarding.domain.model.SignUpData

interface OnboardingRepository {

    suspend fun signIn(email: String, password: String):Resource<Unit>

    suspend fun signUp(email: String, password: String): SignUpCheck

    suspend fun signOut()

    suspend fun updateUserDetails(phoneNumber: String, nationalId: String, name: String): Resource<Unit>

    suspend fun getUserData(): Resource<SignUpData>
}