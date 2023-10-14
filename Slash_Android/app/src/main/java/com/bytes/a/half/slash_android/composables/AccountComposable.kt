package com.bytes.a.half.slash_android.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AccountComposable(onclick: () -> Unit) {
    Column {
        // TODO Sign up / sign in screen
        // Dummy
        Button(onClick = { onclick() }) {
            Text("Open Main")
        }
    }
}