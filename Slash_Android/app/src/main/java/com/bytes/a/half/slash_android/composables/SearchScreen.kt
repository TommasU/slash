package com.bytes.a.half.slash_android.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bytes.a.half.slash_android.Products
import com.bytes.a.half.slash_android.R
import com.bytes.a.half.slash_android.SearchField
import com.bytes.a.half.slash_android.isValidList
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.utils.NetworkUtils

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(searchScreenParams: SearchScreenParams) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val (searchField, progressBar, productListView, offline) = createRefs()
        if (NetworkUtils.isOnline(searchScreenParams.context)) {
            SearchField(
                queryFieldValue = searchScreenParams.queryFieldValue,
                modifier = Modifier.constrainAs(searchField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                onQueryProduct = { query ->
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                    searchScreenParams.onQueryProduct(query)
                })

            if (searchScreenParams.showProgress.value) {
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

            if (searchScreenParams.products.isValidList()) {
                Products(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(productListView) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(searchField.bottom)
                            bottom.linkTo(parent.bottom)
                        }, searchScreenParams.products
                ) { link ->
                    searchScreenParams.onProductClick(link)
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


data class SearchScreenParams(
    val context: Context,
    val queryFieldValue: MutableState<TextFieldValue>,
    val products: MutableList<Product>,
    val showProgress: MutableState<Boolean>,
    val onProductClick: (link: String) -> Unit,
    val onQueryProduct: (query: String) -> Unit
)