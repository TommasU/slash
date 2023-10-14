package com.bytes.a.half.slash_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bytes.a.half.slash_android.composables.ProductCard
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.ui.theme.Slash_AndroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

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
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(products) { index, product ->
            ProductCard(product = product) {
                onclick(product.link)
            }
        }
    }


}


//@Composable
//fun SearchField(
//    queryFieldValue: MutableState<TextFieldValue>,
//    onQueryProduct: (productName: String) -> Unit
//) {
//    TextField(modifier = Modifier
//        .heightIn(40.dp, 200.dp)
//        .fillMaxWidth()
//        .constrainAs(queryField) {
//            start.linkTo(parent.start)
//            end.linkTo(parent.end)
//            top.linkTo(titleContainer.bottom)
//            width = Dimension.fillToConstraints
//        }
//        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
//        value = queryFieldValue.value,
//        onValueChange = { text ->
//            queryFieldValue.value = text
//        },
//        keyboardActions = KeyboardActions(onDone = {
//            if (queryFieldValue.value.text.isValidString()) {
//                onQueryProduct(queryFieldValue.value.text)
//            }
//
//        }),
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//        shape = RoundedCornerShape(8.dp),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = colorAttr(
//                this@IAActivity,
//                R.attr.dividerColor
//            ),
//            textColor = textColor,
//            placeholderColor = colorAttr(
//                context = this@IAActivity,
//                attrId = R.attr.commonTextColor
//            ).copy(alpha = ContentAlpha.medium),
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            cursorColor = textColor
//        ),
//        placeholder = {
//            GeneralTextView(
//                modifier = Modifier.constrainAs(queryField) {
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
//                },
//                text = IAUtils.getPlaceholderBasedOnAction(
//                    this@IAActivity,
//                    mAssistantAction.value ?: ""
//                ),
//                color = colorAttr(
//                    context = this@IAActivity,
//                    attrId = R.attr.commonTextColor
//                ).copy(alpha = ContentAlpha.medium)
//            )
//        },
//        trailingIcon = {
//            Icon(
//                Icons.Filled.Search,
//                "",
//                modifier = Modifier.clickable {
//
//                },
//                tint = colorAttr(
//                    context = this@IAActivity,
//                    attrId = R.attr.commonTextColor
//                )
//            )
//
//        })
//}
