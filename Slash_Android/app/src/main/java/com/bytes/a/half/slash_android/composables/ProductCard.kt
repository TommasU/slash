package com.bytes.a.half.slash_android.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.bytes.a.half.slash_android.SlashHelper.getCompanyLogoUrl
import com.bytes.a.half.slash_android.models.Product


@Composable
fun ProductCard(product: Product, onclick: () -> Unit) {
    Card(
        modifier = Modifier.clickable {
            onclick()
        }.padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(getCompanyLogoUrl(product.website)),
                contentDescription = null, // Add content description as needed
                modifier = Modifier.size(100.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(product.image_url),
                contentDescription = null, // Add content description as needed
                modifier = Modifier.size(100.dp)
            )
            Text(text = product.title, maxLines = 2, overflow = TextOverflow.Ellipsis , modifier = Modifier.padding(8.dp))
            Text(text = product.price.toString(), modifier = Modifier.padding(8.dp) , fontWeight = FontWeight.Bold )
            Text(text = product.rating.toString(), modifier = Modifier.padding(8.dp))

        }
    }

}