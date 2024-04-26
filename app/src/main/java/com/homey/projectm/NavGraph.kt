package com.homey.projectm

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.homey.projectm.presentation.profile.ProfileScreen
import com.homey.projectm.presentation.sign_in.GoogleAuthUIClient
import com.homey.projectm.presentation.sign_in.SignInViewModel
import com.homey.projectm.presentation.sign_in.signInScreen
import com.homey.projectm.presentation.sign_up.SignUpData
import com.homey.projectm.presentation.sign_up.detailsScreen
import com.homey.projectm.presentation.sign_up.signUpScreen
import com.homey.projectm.presentation.welcomeScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    navController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = "welcome_screen",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            )
        }
    ) {
        composable("welcome_screen") {
            welcomeScreen(
                onLogInClick = { navController.navigate("sign_in") },
                onSignUpClick = { navController.navigate("sign_up") }
            )
        }
        composable("sign_in") {
            SignInScreen(navController, googleAuthUIClient)
        }
        composable("profile") {
            val lifecycleScope = rememberCoroutineScope()


            ProfileScreen(
                onSignOut = {
                    lifecycleScope.launch {
                        googleAuthUIClient.signOut()
                    }
                    navController.popBackStack()
                }
            )
        }
        composable("sign_up") {

           signUpScreen(
               navController = navController
           )


        }
        composable("enter_details_screen"){
            detailsScreen(navController = navController)
        }
    }
}

@Composable
fun SignInScreen(
    navController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient
) {
    val signInViewModel = viewModel<SignInViewModel>()
    val state by signInViewModel.state.collectAsStateWithLifecycle()
    val lifecycleScope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(key1 = Unit) {
        if (googleAuthUIClient.getSignedInUser() != null) {
            navController.navigate("profile")
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK)
                lifecycleScope.launch {
                    val signInResult = googleAuthUIClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    signInViewModel.onSignInResult(signInResult)
                }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign In successful",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate("profile")
            signInViewModel.resetState()
        }
    }

    signInScreen(
        navController = navController,
        state = state,
        onSignInClick = {
            lifecycleScope.launch {
                val signInIntentSender = googleAuthUIClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }
    )
}
