package com.homey.projectm.features.onboarding.presentation.sign_up

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.homey.projectm.R
import com.homey.projectm.features.onboarding.domain.model.UIEvent
import com.homey.projectm.generalButton
import com.homey.projectm.generalOutlinedTextBox
import com.homey.projectm.ui.theme.buttonColor
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch

@Composable
fun signUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = context){
        viewModel.uiEvent.collect { event ->
            when(event){
                UIEvent.ChangeScreens -> {
                    Toast.makeText(
                        context,
                        "Sign Up successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("enter_details_screen")
                }
                is UIEvent.ShowSnackBar -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up With", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .height(52.dp)
                .width(61.dp),
            onClick = { viewModel.signUpWithGoogle(context = context) },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = "Google Sign-In"
                )
            }
        }

        Spacer(modifier = Modifier.height(34.dp))
        Text("or")
        Spacer(modifier = Modifier.height(34.dp))

        generalOutlinedTextBox(
            text = viewModel.email,
            onTextChange = { viewModel.email = it },
            placeholderText = "Email"
        )

        Spacer(modifier = Modifier.height(6.dp))

        generalOutlinedTextBox(
            text = viewModel.password,
            onTextChange = { viewModel.password = it },
            placeholderText = "Password"
        )

        Spacer(modifier = Modifier.height(40.dp))

        generalButton(
            buttonItem = {
                Text(
                    "Sign Up",
                    fontSize = 16.sp,
                    color = Color.White
                )
            },
            isLoading = isLoading,
            color = buttonColor,
            onClick = {
                coroutineScope.launch {
                    val result = viewModel.signUpWithEmailAndPassword()
                    if (!result.isSnackBarShown) {
                        Toast.makeText(
                            context,
                            "Sign Up successful",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate("enter_details_screen")
                    } else {
                        Log.e("SignUp", "Error: ${result.errorMessage}")
                        Toast.makeText(
                            context,
                            result.errorMessage ?: "Sign up failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },
            height = 36,
            width = 108
        )
    }

    Column(modifier = Modifier){

    }
}