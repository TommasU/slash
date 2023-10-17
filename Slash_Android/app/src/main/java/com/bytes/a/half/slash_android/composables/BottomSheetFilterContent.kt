package com.bytes.a.half.slash_android.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytes.a.half.slash_android.R



@Composable
fun BottomSheetListItem(icon: Int, title: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(55.dp)
            .background(color = colorResource(id = R.color.purple_200)
    )) {
        Icon(painter = painterResource(id = icon), contentDescription = "Filter", tint = Color.White)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "Filter", color = Color.White)
    }
}

@Composable
fun BottomSheetContent() {
    val context = LocalContext.current
    Column {
        BottomSheetListItem(
            icon = R.drawable.ic_launcher_background,
            title = "Price: High to Low",
            onItemClick = { title ->
                Toast.makeText(
                    context,
                    title,
                    Toast.LENGTH_SHORT
                ).show()
            })
        BottomSheetListItem(
            icon = R.drawable.ic_launcher_background,
            title = "Price: Low to High",
            onItemClick = { title ->
                Toast.makeText(
                    context,
                    title,
                    Toast.LENGTH_SHORT
                ).show()
            })
        BottomSheetListItem(
            icon = R.drawable.ic_launcher_background,
            title = "Rating : High to low",
            onItemClick = { title ->
                Toast.makeText(
                    context,
                    title,
                    Toast.LENGTH_SHORT
                ).show()
            })
        BottomSheetListItem(
            icon = R.drawable.ic_launcher_background,
            title = "Rating : Low to High",
            onItemClick = { title ->
                Toast.makeText(
                    context,
                    title,
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(icon = R.drawable.ic_launcher_background, title = "Filter", onItemClick = { })
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent()
}