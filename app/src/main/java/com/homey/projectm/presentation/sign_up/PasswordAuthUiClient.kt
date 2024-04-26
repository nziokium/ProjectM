package com.homey.projectm.presentation.sign_up

import android.util.Log
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.homey.projectm.presentation.sign_in.SignInResult
import com.homey.projectm.presentation.sign_in.UserData
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException


class PasswordAuthUiClient() {
    private val auth = Firebase.auth

    private val db = Firebase.firestore


    suspend fun signUpUserWithEmailAndPassword(
        email: String,
        password: String
    ): SignUpCheck{
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val data = getSignedUpUser()
            data?.uid?.let {
                db.collection("Users").document(it).set(data).addOnSuccessListener {
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
                db.collection("Users")
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




}