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
import com.homey.projectm.features.onboarding.presentation.profile.ProfileScreen
import com.homey.projectm.features.onboarding.presentation.sign_in.signInScreen
import com.homey.projectm.features.onboarding.presentation.sign_up.detailsScreen
import com.homey.projectm.features.onboarding.presentation.sign_up.signUpScreen
import com.homey.projectm.features.onboarding.presentation.welcomeScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    navController: NavHostController
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
            signInScreen(navController)
        }
        composable("profile") {
            val lifecycleScope = rememberCoroutineScope()


            ProfileScreen(navController)
        }
        composable("sign_up") {
            signUpScreen(
                navController
            )
        }
        composable("enter_details_screen"){
            detailsScreen(navController = navController)
        }
    }
}
