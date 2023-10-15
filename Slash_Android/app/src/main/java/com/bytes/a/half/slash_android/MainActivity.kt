package com.bytes.a.half.slash_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.bytes.a.half.slash_android.composables.ProductCard
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.ui.theme.Slash_AndroidTheme
import com.bytes.a.half.slash_android.utils.NetworkUtils
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var slashApi: SlashAPI
    val products = mutableStateListOf<Product>()
    val queryFieldValue = mutableStateOf(TextFieldValue(""))
    val showProgress = mutableStateOf(false)

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
                Scaffold(bottomBar = {
                    SlashBottomNavigation(navController, navBarItems)
                }) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(Color.Black)
                    ) {
                        val (searchField, progressBar, productListView, offline) = createRefs()
                        if (NetworkUtils.isOnline(this@MainActivity)) {
                            SearchField(
                                queryFieldValue = queryFieldValue,
                                modifier = Modifier.constrainAs(searchField) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                },
                                onQueryProduct = { query ->
                                    coroutineScope.launch {
                                        showProgress.value = true
                                        val productList = queryProduct(query)
                                        if (productList.isValidList()) {
                                            products.clear()
                                            products.addAll(productList!!)
                                        }
                                        showProgress.value = false
                                    }
                                })

                            if (showProgress.value) {
                                CircularProgressIndicator(
                                    modifier = Modifier.constrainAs(
                                        progressBar
                                    ) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        top.linkTo(searchField.bottom)
                                        bottom.linkTo(parent.bottom)
                                    })
                            }

                            if (products.isValidList()) {
                                Products(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .constrainAs(productListView) {
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                            top.linkTo(searchField.bottom)
                                            bottom.linkTo(parent.bottom)
                                        }, products
                                ) { link ->
                                    val httpIntent = Intent(Intent.ACTION_VIEW)
                                    httpIntent.data = Uri.parse(link)
                                    startActivity(httpIntent)
                                }
                            }
                        } else {
                            Box(modifier = Modifier.constrainAs(offline) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }) {
                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Filled.WifiOff,
                                        "",
                                        modifier = Modifier.size(50.dp), tint = Color.White
                                    )
                                    Text(stringResource(id = R.string.offline), color = Color.White)
                                }

                            }

                        }
                    }

                }
            }
        }


    }

    suspend fun queryProduct(productName: String): List<Product>? {
        val response = slashApi.getSearch(productName)
        val products = response.body()
        return products
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
            ProductCard(product = product) {
                onclick(product.link)
            }
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
