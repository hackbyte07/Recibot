package com.hackbyte.recibot.authentication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hackbyte.recibot.R
import com.hackbyte.recibot.authentication.presentation.components.CircularLoading
import com.hackbyte.recibot.destinations.LoginScreenDestination
import com.hackbyte.recibot.navigation.AuthenticationNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@AuthenticationNavGraph
@Destination()
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = "login background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                shape = MaterialTheme.shapes.small,
                label = {
                    Text(text = "Email")
                },
                isError = viewModel.emailError,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            Spacer(modifier = Modifier.padding(top = 25.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                shape = MaterialTheme.shapes.small,
                label = {
                    Text(text = "Password")
                },
                isError = viewModel.passwordError,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboard?.hide()
                    }
                )
            )
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Button(
                onClick = {
                    scope.launch {
                        viewModel.signUpWithEmailAndPassword(email, password)
                        if (viewModel.isSignInSuccess) {
                            snackBarHost.showSnackbar(
                                "Verification email sent. Please verify",
                                duration = SnackbarDuration.Long
                            )
                            navigator.navigate(LoginScreenDestination) {
                                launchSingleTop = true
                            }
                        } else {
                            snackBarHost.showSnackbar(
                                viewModel.errorMessage,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Create account")
            }

            Spacer(modifier = Modifier.padding(top = 50.dp))

            TextButton(onClick = {
                navigator.navigate(LoginScreenDestination) {
                    this.launchSingleTop = true
                    popUpTo(LoginScreenDestination)
                }
            }) {
                Text(text = "Back to login", fontSize = 12.sp)
            }
        }
        SnackbarHost(hostState = snackBarHost)
        CircularLoading(show = viewModel.loading)
    }

}