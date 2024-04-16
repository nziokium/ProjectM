package com.homey.projectm.presentation.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.homey.projectm.R
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUIClient(
    private val context: Context,

    //Client from Firebase which will show the dialog to sign in
    private val oneTapClient: SignInClient
) {
   private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e

            //if there is an issue there is no intent being sent
            null
        }

        return result?.pendingIntent?.intentSender
    }

    //Function to deserialize the intent sent back from firebase after the
    // above function sends an intent successfully
    suspend fun signInWithIntent(intent: Intent): SignInResult{

        // Next 3 lines of code are google specific.
        // Other authentication methods may have different ways of doing this
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try{
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run{
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )

        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    //Function to sign out of the account
    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    //Function to map the current firebase user to our own data object (UserData)
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
            UserData(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString()
            )

    }

    private fun buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder() //returns a newly created builder object for the sign in request
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()

                    //Add support for OneTap authentication
                    .setSupported(true)

                    //Add a filter for showing accounts authorized to sign in.
                    //If set to false, it will show all accounts used by the device to sign in
                    //if set to true, it will only show accounts previously used to sign in to the app
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )

            //If you only have one google account on the device, use that one to sign in
            .setAutoSelectEnabled(true)
            .build()
    }

}