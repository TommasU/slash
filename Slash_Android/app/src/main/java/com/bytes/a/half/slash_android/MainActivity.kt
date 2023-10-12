package com.bytes.a.half.slash_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.bytes.a.half.slash_android.composables.ProductCard
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.ui.theme.Slash_AndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val products = ArrayList<Product>()
            products.add(
                Product(
                    1,
                    "tumbler",
                    45,
                    "https://m.media-amazon.com/images/I/91ZBiZOuf0L._AC_SX679_.jpg",
                    "www.amazon.com",
                    5.6,
                    3,
                    "best seller"
                )
            )

            products.add(
                Product(
                    2,
                    "sipper",
                    45,
                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fdespicableme.fandom.com%2Fwiki%2FKevin_%2528Despicable_Me_2%2529&psig=AOvVaw3-IL3zbPD3AWexabX6-YXJ&ust=1697064549293000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCIiU0Z_I7IEDFQAAAAAdAAAAABAE",
                    "www.amazon.com",
                    5.6,
                    3,
                    "best seller"
                )
            )
            Slash_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Products(modifier = Modifier.fillMaxSize(), products) { link ->
                        val httpIntent = Intent(Intent.ACTION_VIEW)
                        httpIntent.data = Uri.parse(link)
                        startActivity(httpIntent)
                    }
                }
            }
        }
    }
}


@Composable
fun Products(
    modifier: Modifier = Modifier,
    products: List<Product>, onclick: (link: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(products) { index, product ->
            ProductCard(product = product) {
                onclick(product.link)
            }
        }
    }


}
