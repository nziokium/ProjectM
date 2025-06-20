package com.homey.projectm.features.onboarding.domain.useCases

import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import javax.inject.Inject

class SignInWithEmailAndPassword @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend fun execute(email: String, password: String): Resource<Unit> {
        return repository.signIn(email, password)
    }
}