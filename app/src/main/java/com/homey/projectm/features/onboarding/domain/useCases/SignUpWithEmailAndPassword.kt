package com.homey.projectm.features.onboarding.domain.useCases

import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignUpCheck
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import javax.inject.Inject

class SignUpWithEmailAndPassword @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend fun execute(email: String, password: String): SignUpCheck {
        return repository.signUp(email, password)
    }
}