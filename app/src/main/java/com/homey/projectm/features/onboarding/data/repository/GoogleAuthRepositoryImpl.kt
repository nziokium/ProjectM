package com.homey.projectm.features.onboarding.data.repository


import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignInResult
import com.homey.projectm.features.onboarding.domain.repostiory.GoogleAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import com.homey.projectm.R
import com.homey.projectm.features.onboarding.domain.model.UserData

class GoogleAuthRepositoryImpl : GoogleAuthRepository {

    override suspend fun signIn(context: Context): Flow<Resource<SignInResult>> = flow {
        val credentialManager = CredentialManager.create(context)
        emit(Resource.Loading())

        // Create a nonce to prevent Person In the Middle attacks
        val rawNonce = UUID.randomUUID().toString()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(rawNonce.toByteArray(StandardCharsets.UTF_8))
        val hashedNonce = Base64.getUrlEncoder().withoutPadding().encodeToString(digest)

        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setAutoSelectEnabled(true)
                .setNonce(hashedNonce)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is CustomCredential) {
                Log.d("ProjectM", "Received CustomCredential instead of GoogleIdTokenCredential")

                val idTokenKey = "com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN"
                val nameKey = "com.google.android.libraries.identity.googleid.BUNDLE_KEY_DISPLAY_NAME"
                val emailKey = "com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID"
                val profilePhotoKey = "com.google.android.libraries.identity.googleid.BUNDLE_KEY_PROFILE_PICTURE_URI"

                val idToken = credential.data.getString(idTokenKey)
                val name = credential.data.getString(nameKey)
                val email = credential.data.getString(emailKey)
                val profilePicture = credential.data.getString(profilePhotoKey)

                Log.d("ProjectM", "Credential Data Keys: ${credential.data.keySet()}")

                if (idToken != null) {
                    val userData = UserData(
                        userId = idToken,
                        username = name,
                        profilePictureUrl = profilePicture
                    )
                    emit(Resource.Success(SignInResult(data = userData, null)))
                } else {
                    Log.e("ProjectM", "id_token not found in CustomCredential")
                    emit(Resource.Error("Failed to extract ID Token. Please try again."))
                }
            } else {
                emit(Resource.Error("Unexpected credential type returned."))
            }

        } catch (e: GetCredentialCancellationException) {
            Log.d("ProjectM", "User cancelled the sign-in flow: ${e.message}")
            emit(Resource.Error("Sign-in cancelled by user."))

        } catch (e: NoCredentialException) {
            Log.e("ProjectM", "No credential found: ${e.message}", e)
            emit(Resource.Error("No Google account found. Please add an account or try another method."))

        } catch (e: IOException) {
            Log.e("ProjectM", "Network error: ${e.message}", e)
            emit(Resource.Error("No network connection. Please try again."))

        } catch (e: Exception) {
            Log.e("ProjectM", "Unexpected error: ${e.message}", e)
            emit(Resource.Error("An unexpected error occurred. Please try again."))
        }
    }

    override suspend fun signOut(context: Context) {
        val credentialManager = CredentialManager.create(context)

        try {
            val request = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(request)
        } catch (e: Exception) {
            Log.e("ProjectM", "Error during sign out: ${e.message}", e)
        }

    }
}