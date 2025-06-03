package com.homey.projectm.features.onboarding.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homey.projectm.core.util.Resource
import com.homey.projectm.features.onboarding.domain.model.SignUpCheck
import com.homey.projectm.features.onboarding.domain.model.SignUpData
import com.homey.projectm.features.onboarding.domain.repostiory.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class OnboardingRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): OnboardingRepository {

    private val uid = auth.currentUser?.uid

    //Sign in with email and password
    override suspend fun signIn(email: String, password: String): Resource<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
            Resource.Success(data = Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("OnboardingRepository", "Authentication Failed", e)
            Resource.Error("Authentication failed: ${e.message ?: "Unknown error"}")
        }
    }

    //Sign up with email, password and other additional parameters if needed
    override suspend fun signUp(
        email: String,
        password: String
    ): SignUpCheck {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val data = getSignedUpUser()
            data?.uid?.let {
                firestore
                    .collection("Users")
                    .document(it)
                    .set(data)
                    .addOnSuccessListener {
                    Log.d("Message", "Data saved")
                }.addOnFailureListener {
                    Log.d("Error", "save failed")
                }
            }
            SignUpCheck(
                isSnackBarShown = false,
                errorMessage = null
            )
        } catch (e: Exception) {
            Log.d("Message", "${e.message}")
            if (e is IllegalArgumentException){
                SignUpCheck(
                    isSnackBarShown = true,
                    errorMessage = "Email/Password Field is Empty"
                )
            }else{
                SignUpCheck(
                    isSnackBarShown = true,
                    errorMessage = e.message
                )
            }
        }
    }

    //Sign out function
    override suspend fun signOut() {
        try {

            auth.signOut()
            Log.d("OnboardingRepository", "Successfully signed out")
        } catch (e: Exception) {
            Log.e("OnboardingRepository", "Sign-out failed", e)
        }
    }




    suspend fun updateUserDetails(phoneNumber: String, nationalId: String, name: String) {
        val newData = auth.currentUser?.run {
            SignUpData(
                email = email,
                password = null,
                uid = uid,
                name = name,
                phoneNumber = phoneNumber,
                nationalId = nationalId
            )
        }

        val updates = hashMapOf(
            "name" to name,
            "phoneNumber" to phoneNumber,
            "nationalId" to nationalId
        )

        newData?.uid?.let {
            try {
                firestore.collection("Users")
                    .document(it)
                    .update(updates as Map<String, Any>)
                    .await()
                // Update successful
            } catch (e: Exception) {
                // Handle any errors
                Log.e("TAG", "Error updating user details", e)
            }
        }
    }

    suspend fun getUserData(): Resource<SignUpData> {
        return try {
            val document = firestore
                .collection("Users")
                .document(uid.toString())
                .get()
                .await()


            if (document != null && document.exists()) {
                Resource.Success(SignUpData(
                    email = document.getString("email"),
                    password = null,
                    uid = document.getString("uid"),
                    name = document.getString("name"),
                    phoneNumber = document.getString("phoneNumber"),
                    nationalId = document.getString("nationalId")
                ))
            } else {
                Resource.Error("User does not exist") // Return empty SignUpData if document doesn't exist
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")// Return empty SignUpData if there's any exception
        }
    }



    //Function to get current user from Firebase Auth
    private fun getSignedUpUser(): SignUpData? = auth.currentUser?.run {
        SignUpData(
            email = email,
            password = null,
            uid = uid,
            name = displayName,
            phoneNumber = null,
            nationalId = null

        )
    }
}