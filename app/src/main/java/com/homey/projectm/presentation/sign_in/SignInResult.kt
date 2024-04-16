package com.homey.projectm.presentation.sign_in

//This is a data class containing the user data and error if something goes wrong
data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
)

//Data class containing all the user data obtained from your google account.
data class UserData(
    //user Id is a unique identity from firebase
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?
)