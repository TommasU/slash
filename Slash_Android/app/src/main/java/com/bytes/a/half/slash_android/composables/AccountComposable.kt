@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.bytes.a.half.slash_android.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytes.a.half.slash_android.ui.theme.Purple80

@Composable
fun AccountComposable(onclick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        // TODO Sign up / sign in screen
        // Dummy
        Text(text = "Login or sign up to continue")
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentHeight()) {
            Column(modifier = Modifier
                .wrapContentHeight()
            ) {
                val emailFieldValue = remember { mutableStateOf(TextFieldValue()) }

                OutlinedTextField(value = emailFieldValue.value,
                    onValueChange = { emailFieldValue.value = it },
                    placeholder = { Text(text = "Email id") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Purple80,
                        unfocusedBorderColor = Color.Black,
                        placeholderColor = Color.Gray,
                        textColor = Color.Black
                    ))
                val passwordFieldValue = remember { mutableStateOf(TextFieldValue()) }

                OutlinedTextField(value = passwordFieldValue.value,
                    onValueChange = { passwordFieldValue.value = it },
                    modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                    placeholder = { Text(text = "Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Purple80,
                        unfocusedBorderColor = Color.Black,
                        placeholderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(20.dp),horizontalArrangement = Arrangement.SpaceEvenly){
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor =Color( 0xff4caf50) ),
                        onClick = { onclick() }) {
                        Text(text = "Login",
                            modifier = Modifier.padding(10.dp), color= Color.Black)
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color( 0xff039be5)),
                        onClick = { onclick() }) {
                        Text(text = "Sign up",
                            modifier = Modifier.padding(10.dp), color= Color.Black)
                    }
                }
            }
//        Button(onClick = { onclick() }) {
//            Text("Open Main")
        }
    }
}
