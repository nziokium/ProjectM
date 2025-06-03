package com.homey.projectm.features.onboarding.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.homey.projectm.generalButton
import com.homey.projectm.ui.theme.buttonColor


@Composable
fun welcomeScreen(
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Welcome", fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(232.dp))

        generalButton(
            buttonItem =
            {
                Text(
                    "Log in",
                    fontSize = 24.sp,
                    color = Color.White
                )
            },
            color = buttonColor,
            onClick = { onLogInClick() },
            height = 50,
            width = 305
        )

        Spacer(modifier = Modifier.height(24.dp))

        generalButton(
            buttonItem = {
                Text(
                    "Sign Up",
                    fontSize = 24.sp,
                    color = Color.Black
                )
            },
            color = Color.White,
            onClick = { onSignUpClick() },
            border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Black)),
            height = 50,
            width = 305
        )
    }
}

