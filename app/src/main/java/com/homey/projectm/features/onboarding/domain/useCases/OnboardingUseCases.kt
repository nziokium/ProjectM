package com.homey.projectm.features.onboarding.domain.useCases

data class OnboardingUseCases(
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val signUpWithEmailAndPassword: SignUpWithEmailAndPassword,
    val signOut: SignOut,
    val signInWithGoogle: SignInWithGoogle,
    val updateUserDetails: UpdateUserDetails,
    val getUserData: GetUserData
)
