package com.homey.projectm.features.onboarding.domain.repostiory

import android.app.Activity
import android.content.Context
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignInResult
import kotlinx.coroutines.flow.Flow


interface GoogleAuthRepository {
    suspend fun signIn(context: Context): Flow<Resource<SignInResult>>// Returns the Google ID token

    suspend fun signOut(context: Context)
}