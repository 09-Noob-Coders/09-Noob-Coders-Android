package com.example.a09_noob_coders_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Code
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            androidx.compose.material.Surface() {

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                systemUiController.setSystemBarsColor(
                    color = BluePrimary,
                    darkIcons = useDarkIcons
                )

                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser

                if (currentUser != null) {
                    val myIntent =
                        Intent(this@LoginActivity, EventsListActivity::class.java)
                    this@LoginActivity.startActivity(myIntent)
                    finish()
                }

                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BluePrimary)
                        .padding(40.dp)
                        .scrollable(
                            state = rememberScrollState(),
                            orientation = Orientation.Vertical
                        ),
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp / 2.5).dp),
                        contentAlignment = Center
                    ) {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Rounded.Code,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                            )
                            Text(text = "DoCode", fontSize = 35.sp)
                        }
                    }

                    val emailTF = remember {
                        mutableStateOf("")
                    }

                    val passwordTF = remember {
                        mutableStateOf("")
                    }

                    val isPasswordVisible = remember {
                        mutableStateOf(false)
                    }


                    OutlinedTextField(
                        value = emailTF.value,
                        onValueChange = {
                            emailTF.value = it
                        },
                        label = {
                            Text(text = "Email")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Email Icon",
                            )
                        },
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, top = 50.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(40.dp),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = passwordTF.value,
                        onValueChange = {
                            passwordTF.value = it
                        },
                        label = {
                            Text(text = "Password")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Password,
                                contentDescription = "Password Icon",
                            )
                        },
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .fillMaxWidth()
                            .padding(bottom = 30.dp, top = 30.dp),
                        trailingIcon = {
                            if (isPasswordVisible.value) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Visibility off Icon",
                                    modifier = Modifier
                                        .clickable {
                                            isPasswordVisible.value =
                                                !isPasswordVisible.value
                                        }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Visibility Icon",
                                    modifier = Modifier
                                        .clickable {
                                            isPasswordVisible.value =
                                                !isPasswordVisible.value
                                        }
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        shape = RoundedCornerShape(40.dp),
                        singleLine = true,
                    )

                    Button(
                        onClick = {

                            auth.signInWithEmailAndPassword(emailTF.value, passwordTF.value)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val myIntent =
                                            Intent(
                                                this@LoginActivity,
                                                EventsListActivity::class.java
                                            )
                                        this@LoginActivity.startActivity(myIntent)
                                        finish()
                                        Log.i("SignInInfo", "signInWithEmail:success")
                                    } else {
                                        Log.i(
                                            "SignInInfo",
                                            "signInWithEmail:failure",
                                            task.exception
                                        )
                                        Toast.makeText(
                                            applicationContext, "Unable to SignIn",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            BlueSecondary
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Register", modifier = Modifier.clickable {
                        val myIntent =
                            Intent(this@LoginActivity, RegisterActivity::class.java)
                        this@LoginActivity.startActivity(myIntent)
                        finish()
                    })
                }
            }
        }
    }
}

