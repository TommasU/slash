package com.bytes.a.half.slash_android.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.bytes.a.half.slash_android.models.Product


@Composable
fun ProductCard(product: Product, onclick: () -> Unit) {
    Card(
        modifier = Modifier.clickable {
            onclick()
        }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(product.link),
                contentDescription = null, // Add content description as needed
                modifier = Modifier.size(100.dp)
            )
            Text(text = product.title)
            Text(text = product.rating.toString())

        }
    }

}