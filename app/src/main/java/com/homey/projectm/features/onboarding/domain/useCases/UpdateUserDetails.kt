package com.homey.projectm.features.onboarding.domain.useCases

import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import javax.inject.Inject

class UpdateUserDetails @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend fun execute(name: String, nationalId: String, phoneNumber: String): Resource<Unit> {
        return repository.updateUserDetails(phoneNumber, nationalId, name)
    }
}