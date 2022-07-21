package com.hackbyte.recibot.authentication.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    show: Boolean,
    softwareKeyboardController: SoftwareKeyboardController?,
    setShowDialog: (Boolean) -> Unit,
    onClick: (String) -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }
    val focus = LocalFocusManager.current

    if (show) {
        Dialog(onDismissRequest = { setShowDialog(false) }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.LightGray
            ) {
                Column(
                    Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Reset password",
                            modifier = Modifier
                                .padding(start = 15.dp, top = 10.dp)
                                .fillMaxWidth(0.85f)
                        )
                        IconButton(
                            onClick = { setShowDialog(false) },
                            modifier = Modifier.padding(end = 15.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                "close the dialog"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        maxLines = 1,
                        shape = RoundedCornerShape(50.dp),
                        label = {
                            Text(text = "Email")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                softwareKeyboardController?.hide()
                                focus.clearFocus()
                            }
                        )
                    )
                    Spacer(modifier = Modifier.padding(top = 25.dp))
                    Button(
                        onClick = {
                            onClick(email)
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomDialog() {

}