package com.homey.projectm.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.homey.projectm.generalButton
import com.homey.projectm.generalInfoTextBox
import com.homey.projectm.presentation.sign_in.UserData
import com.homey.projectm.presentation.sign_up.SignUpData
import com.homey.projectm.ui.theme.buttonColor

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {

    val userData by viewModel.userData



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
            text = "Sign Out",
            color = buttonColor,
            fontSize = 16.sp,
            onClick = { onSignOut() },
            border = null,
            height = 34,
            width = 194,
            textColor = Color.White
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
