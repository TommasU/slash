package com.bytes.a.half.slash_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bytes.a.half.slash_android.composables.ProductCard
import com.bytes.a.half.slash_android.composables.SearchScreenParams
import com.bytes.a.half.slash_android.composables.WishListScreenParams
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.ui.theme.Slash_AndroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var slashApi: SlashAPI
    val products = mutableStateListOf<Product>()
    val wishListProducts = mutableStateListOf<Product>()
    val queryFieldValue = mutableStateOf(TextFieldValue(""))
    val showProgress = mutableStateOf(false)
    val showWishListProgress = mutableStateOf(false)
    val appbarTitle = mutableStateOf("")


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slashApi = SlashAPIHelper.getInstance().create(SlashAPI::class.java)
        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val navBarItems =
                listOf(BottomNavigationScreens.Search, BottomNavigationScreens.Wishlist)
            Slash_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    bottomBar = {
                        SlashBottomNavigation(navController, navBarItems, appbarTitle)
                    },
                    topBar = {
                        TopAppBar(title = {
                            Text(appbarTitle.value)
                        })
                    },
                ) {
                    val searchScreenParams =
                        SearchScreenParams(
                            this,
                            queryFieldValue,
                            products = products,
                            showProgress,
                            onProductClick = { link ->
                                openLink(link)
                            },
                            onQueryProduct = { productName ->
                                showProgress.value = true
                                coroutineScope.launch {
                                    val productList = queryProduct(productName)
                                    products.clear()
                                    if (productList != null) {
                                        products.addAll(productList)
                                    }
                                    showProgress.value = false
                                }

                            })

                    val wishListScreenParams = WishListScreenParams(
                        this,
                        wishListProducts,
                        showWishListProgress,
                        onProductClick = { link ->
                            openLink(link)
                        })
                    SlashNavigationConfiguration(
                        it,
                        navController,
                        searchScreenParams,
                        wishListScreenParams
                    )
                }
            }
        }


    }

    suspend fun queryProduct(productName: String): List<Product>? {
        val response = slashApi.getSearch(productName)
        val products = response.body()
        return products
    }


    private fun openLink(link: String) {
        val httpIntent = Intent(Intent.ACTION_VIEW)
        httpIntent.data = Uri.parse(link)
        startActivity(httpIntent)
    }


}


@Composable
fun Products(
    modifier: Modifier = Modifier, products: List<Product>, onclick: (link: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), modifier = modifier
    ) {
        itemsIndexed(products) { index, product ->
            ProductCard(product = product, onclick = {
                if (product.link.isValidString()) {
                    onclick(product.link!!)
                }
            }, onAddToWishList = {
                SlashAPIHelper.addToWishList(product)
            })
        }
    }


}


@Composable
fun SearchField(
    queryFieldValue: MutableState<TextFieldValue>,
    onQueryProduct: (productName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(modifier = modifier
        .heightIn(50.dp)
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        value = queryFieldValue.value,
        onValueChange = { text ->
            queryFieldValue.value = text
        },
        keyboardActions = KeyboardActions(onDone = {
            if (queryFieldValue.value.text.isValidString()) {
                onQueryProduct(queryFieldValue.value.text)
            }

        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = Color.White.copy(alpha = ContentAlpha.medium)
            )
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Search,
                "",
                modifier = Modifier.clickable {
                    if (queryFieldValue.value.text.isValidString()) {
                        onQueryProduct(queryFieldValue.value.text)
                    }
                },
                tint = Color.White
            )

        })
}
