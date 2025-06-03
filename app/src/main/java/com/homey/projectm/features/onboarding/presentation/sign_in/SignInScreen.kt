package com.homey.projectm.features.onboarding.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.homey.projectm.R
import com.homey.projectm.features.onboarding.domain.model.UIEvent
import com.homey.projectm.generalButton
import com.homey.projectm.generalOutlinedTextBox
import com.homey.projectm.ui.theme.buttonColor

@Composable
fun signInScreen(
    navController: NavController,
    viewModel: SignInViewModel = viewModel()
) {


    val context = LocalContext.current //Used in making Toast messages


    LaunchedEffect(key1 = context){
        viewModel.uiEvent.collect { event ->
            when(event){
                UIEvent.ChangeScreens -> {
                    Toast.makeText(
                        context,
                        "Sign In successful",
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Sign In With",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .height(52.dp)
                .width(61.dp),
            onClick = { viewModel.signInWithGoogle(context) },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 2.dp, color = Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = null,
                    alignment = Alignment.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            "or"
        )
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
                    "Log In",
                    fontSize = 16.sp,
                    color = Color.White
                )
            },
            color = buttonColor,
            onClick = {
                viewModel.onSignInRequest()
                navController.navigate("profile")
            },
            height = 36,
            width = 108
        )

    }

}
