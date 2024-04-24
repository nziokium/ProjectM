package com.homey.projectm.presentation.sign_in

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.homey.projectm.presentation.sign_up.SignUpData
import kotlinx.coroutines.tasks.await

class SignInWithPassUiClient {
    private val auth = Firebase.auth
    suspend fun signInUserWithEmailAndPassword(
        email: String, password: String
    ) {
        try {
            val user = auth.signInWithEmailAndPassword(email, password)
                .await()
        } catch (e: Exception) {
            Log.d("Error", "Authentication Failed")
            e.printStackTrace()


        }


    }
}