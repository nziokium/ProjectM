package com.homey.projectm.features.onboarding.domain.useCases

import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignUpData
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import javax.inject.Inject

class GetUserData @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend fun execute(): Resource<SignUpData> {
        return repository.getUserData()
    }
}