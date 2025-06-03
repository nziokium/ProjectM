package com.homey.projectm.features.onboarding.presentation.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.homey.projectm.generalButton
import com.homey.projectm.generalOutlinedTextBox
import com.homey.projectm.ui.theme.buttonColor


@Composable
fun detailsScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            "Welcome! Let us get you set up.",
            fontSize = 24.sp,
        )


        Spacer(modifier = Modifier.height(72.dp))

        generalOutlinedTextBox(
            text = viewModel.userName,
            onTextChange = { viewModel.userName = it },
            placeholderText = "Full Name"

        )

        Spacer(modifier = Modifier.height(8.dp))

        generalOutlinedTextBox(
            text = viewModel.phoneNumber,
            onTextChange = { viewModel.phoneNumber = it },
            placeholderText = "Phone Number"

        )

        Spacer(modifier = Modifier.height(8.dp))

        generalOutlinedTextBox(
            text = viewModel.idNumber,
            onTextChange = { viewModel.idNumber = it },
            placeholderText = "ID Number"
        )
        Spacer(modifier = Modifier.height(63.dp))


        generalButton(
            buttonItem =
            {
                Text(
                    "Finish",
                    fontSize = 16.sp,
                    color = Color.White
                )
            },
            color = buttonColor,

            onClick = {
                viewModel.updateUserDetails()
                navController.navigate("profile")
            },
            height = 34,
            width = 194
        )
    }
}