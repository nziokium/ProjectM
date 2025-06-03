package com.homey.projectm.features.onboarding.domain.useCases

import android.content.Context
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.repostiory.GoogleAuthRepository
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val googleAuthRepository: GoogleAuthRepository
) {
    suspend fun execute(context: Context) {
        googleAuthRepository.signOut(context)
        onboardingRepository.signOut()
    }
}