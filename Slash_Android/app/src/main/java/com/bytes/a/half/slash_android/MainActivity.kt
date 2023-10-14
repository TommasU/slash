package com.bytes.a.half.slash_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bytes.a.half.slash_android.composables.ProductCard
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.ui.theme.Slash_AndroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var slashApi: SlashAPI

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slashApi = SlashAPIHelper.getInstance().create(SlashAPI::class.java)
        lifecycleScope.launch {
            val response = slashApi.getSearch("milk")
            val products = response.body()
            launch(Dispatchers.Main) {
                setContent {

                    val navController = rememberNavController()

                    val navBarItems =
                        listOf(BottomNavigationScreens.Search, BottomNavigationScreens.Wishlist)


                    Slash_AndroidTheme {
                        // A surface container using the 'background' color from the theme
                        Scaffold(bottomBar = {
                            SlashBottomNavigation(navController, navBarItems)
                        }) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                if (products != null) {
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
            }
        }


    }
}


@Composable
fun Products(
    modifier: Modifier = Modifier, products: List<Product>, onclick: (link: String) -> Unit
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
