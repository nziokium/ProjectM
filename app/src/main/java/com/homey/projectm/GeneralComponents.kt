package com.homey.projectm

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

@Composable
fun generalButton(
    text: String,
    color: Color,
    fontSize: TextUnit,
    onClick: () -> Unit,
    border: BorderStroke? = null,
    height: Int,
    width: Int,
    textColor: Color = Color.Black
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            Text(
                text,
                fontSize = fontSize,
                color = textColor
            )
        }
    }
}


@Composable
fun generalOutlinedTextBox(
    text: String,
    onTextChange: () -> Unit,
    placeholderText: String
) {

        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded corners for the text field
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            placeholder = { Text(placeholderText) },
            singleLine = true,
            maxLines = 1

        )
    }