package com.homey.projectm.presentation.sign_up

import android.health.connect.datatypes.units.Length
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.homey.projectm.R
import com.homey.projectm.generalButton
import com.homey.projectm.generalOutlinedTextBox
import com.homey.projectm.loading
import com.homey.projectm.ui.theme.buttonColor
import kotlinx.coroutines.launch

@Composable
fun signUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel(),

){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Sign Up With",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .height(52.dp)
                .width(61.dp),
            onClick = { /*TODO*/ },
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
            onTextChange = { viewModel.password = it},
            placeholderText = "Password"
        )

        Spacer(modifier = Modifier.height(40.dp))


        generalButton(
            buttonItem =
            {
            if (!viewModel.isLoading){
                Text(
                    "Sign Up",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            else{
                loading()
            }
            },
            color = buttonColor,
            onClick = {

                // Start collecting isLoading when the button is clicked
                coroutineScope.launch {
                    val bool = viewModel.signUpWithEmailAndPassword()
                    if (!bool.isSnackBarShown){
                        navController.navigate("enter_details_screen")
                        
                        Toast.makeText(
                            context,
                            "Sign Up successful",
                            Toast.LENGTH_LONG

                        ).show()
                    }
                    else{
                        Log.d("Message", "Error")
                        Toast.makeText(
                            context,
                            "${viewModel.signUpWithEmailAndPassword().errorMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            },
            height = 36,
            width = 108
        )


    }
}