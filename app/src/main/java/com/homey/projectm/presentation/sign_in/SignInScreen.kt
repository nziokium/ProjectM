package com.homey.projectm.presentation.sign_in

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
import com.homey.projectm.R
import com.homey.projectm.generalButton
import com.homey.projectm.generalOutlinedTextBox
import com.homey.projectm.ui.theme.buttonColor

@Composable
fun signInScreen(
    state: SignInState, onSignInClick: () -> Unit
) {
    //To be removed
    var nameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    val onNameTextChanged: (String) -> Unit = { newNameText ->
        nameText = newNameText
    }

    val onPasswordTextChanged: (String) -> Unit = { newPasswordText ->
        passwordText = newPasswordText
    }

    //Remove above values


    val context = LocalContext.current //Used in making Toast messages
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context, error, Toast.LENGTH_LONG
            ).show()
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
            onClick = { onSignInClick() },
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
            text = nameText,
           onTextChange = { onNameTextChanged(nameText) },
            placeholderText = "Email"
        )

        Spacer(modifier = Modifier.height(6.dp))

        generalOutlinedTextBox(
            text = passwordText,
            onTextChange = { onPasswordTextChanged(passwordText) },
            placeholderText = "Password"
        )

        Spacer(modifier = Modifier.height(40.dp))

        generalButton(
            text = "Log In",
            color = buttonColor,
            fontSize = 16.sp,
            onClick = {/*TODO*/},
            height = 36,
            width = 108,
            textColor = Color.White


        )


    }

}
