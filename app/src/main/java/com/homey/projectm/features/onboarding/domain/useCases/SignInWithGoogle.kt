package com.homey.projectm.features.onboarding.domain.useCases

import android.content.Context
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignInResult
import com.homey.projectm.features.onboarding.domain.repostiory.GoogleAuthRepository
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogle @Inject constructor(
    private val repository: GoogleAuthRepository
) {
    suspend fun execute(context: Context): Flow<Resource<SignInResult>> {
        return repository.signIn(context)
    }
}