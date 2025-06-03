package com.homey.projectm.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homey.projectm.features.onboarding.data.repository.OnboardingRepositoryImpl
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): OnboardingRepository = OnboardingRepositoryImpl(auth, firestore)
}