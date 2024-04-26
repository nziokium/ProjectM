package com.homey.projectm

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homey.projectm.ui.theme.buttonColor
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun generalButton(
    buttonItem: @Composable ()-> Unit,
    color: Color,
    onClick:() -> Unit,
    border: BorderStroke? = null,
    height: Int,
    width: Int
) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .height(height.dp)
            .width(width.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        border = border

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            buttonItem()
        }
    }
}


@Composable
fun generalOutlinedTextBox(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String
) {

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded corners for the text field
            textStyle = TextStyle(
                color = Color.Black, fontSize = 16.sp
            ),
            placeholder = { Text(placeholderText) },
            singleLine = true,
            maxLines = 1

        )
}

@Composable
fun PreviewGeneralInfoTextBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        generalInfoTextBox(leftText = "Name", rightText = "Andrew Nzioki")
    }
}

@Composable
fun generalInfoTextBox(
    leftText: String,
    rightText: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        border = BorderStroke(width = 2.dp, color = buttonColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(leftText)
            Text(
                rightText,
                style = TextStyle(
                    color = Color(0xFF666666)
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun loading() {
    val rotation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(50.dp)) {
            val radius = size.minDimension / 2
            val strokeWidth = 4.dp.toPx()

            drawArc(
                color = Color.Gray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            val startAngle = 0f
            val endAngle = rotation.value

            drawArc(
                color = Color.White,
                startAngle = startAngle,
                sweepAngle = endAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )



        }
    }
}
