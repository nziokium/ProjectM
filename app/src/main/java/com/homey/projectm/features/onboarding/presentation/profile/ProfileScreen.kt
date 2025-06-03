package com.homey.projectm.features.onboarding.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.homey.projectm.generalButton
import com.homey.projectm.generalInfoTextBox
import com.homey.projectm.ui.theme.buttonColor

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {

    val userData by viewModel.userData
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            topAppBar()
        }

            Spacer(modifier = Modifier.height(16.dp))

            generalInfoTextBox(
                "Name",
                "${userData.name}"
            )
            Spacer(modifier = Modifier.height(16.dp))


            generalInfoTextBox(
                "Email",
                "${userData.email}"
            )
            Spacer(modifier = Modifier.height(16.dp))


            generalInfoTextBox(
                "ID Number",
                "${userData.nationalId}"
            )
            Spacer(modifier = Modifier.height(16.dp))


            generalInfoTextBox(
                "Phone Number",
                "${userData.phoneNumber}"
            )
            Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(284.dp))
        generalButton(
            buttonItem =
            {
                Text(
                    "Sign Out",
                    fontSize = 16.sp,
                    color = Color.White
                )
            },
            color = buttonColor,
            onClick = {
                viewModel.signOut(context = context)
                navController.popBackStack()
                      },
            border = null,
            height = 34,
            width = 194

        )
    }

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun topAppBar(

) {
    TopAppBar(
        title = { Text("Home", fontSize = 36.sp) })
}
