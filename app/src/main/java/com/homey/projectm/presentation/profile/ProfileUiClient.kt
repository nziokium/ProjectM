package com.homey.projectm.presentation.profile

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.homey.projectm.presentation.sign_up.SignUpData
import kotlinx.coroutines.tasks.await

class ProfileUiClient() {
    private val auth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    private val uid = auth.currentUser?.uid

    suspend fun getUserData(): SignUpData {
        return try {
            val document = db.collection("Users").document(uid.toString()).get().await()
            if (document != null && document.exists()) {
                SignUpData(
                    email = document.getString("email"),
                    password = null,
                    uid = document.getString("uid"),
                    name = document.getString("name"),
                    phoneNumber = document.getString("phoneNumber"),
                    nationalId = document.getString("nationalId")
                )
            } else {
                SignUpData() // Return empty SignUpData if document doesn't exist
            }
        } catch (e: Exception) {
            SignUpData() // Return empty SignUpData if there's any exception
        }
    }
}
