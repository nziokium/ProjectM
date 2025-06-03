package com.homey.projectm.di

import com.homey.projectm.features.onboarding.domain.repostiory.GoogleAuthRepository
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import com.homey.projectm.features.onboarding.domain.useCases.GetUserData
import com.homey.projectm.features.onboarding.domain.useCases.OnboardingUseCases
import com.homey.projectm.features.onboarding.domain.useCases.SignInWithEmailAndPassword
import com.homey.projectm.features.onboarding.domain.useCases.SignInWithGoogle
import com.homey.projectm.features.onboarding.domain.useCases.SignOut
import com.homey.projectm.features.onboarding.domain.useCases.SignUpWithEmailAndPassword
import com.homey.projectm.features.onboarding.domain.useCases.UpdateUserDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideOnboardingUseCases(
        googleAuthRepository: GoogleAuthRepository,
        onboardingRepository: OnboardingRepository
    ): OnboardingUseCases = OnboardingUseCases(
        signUpWithEmailAndPassword = SignUpWithEmailAndPassword(onboardingRepository),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(onboardingRepository),
        signOut = SignOut(onboardingRepository, googleAuthRepository),
        signInWithGoogle = SignInWithGoogle(googleAuthRepository),
        updateUserDetails = UpdateUserDetails(onboardingRepository),
        getUserData = GetUserData(onboardingRepository)
    )

}