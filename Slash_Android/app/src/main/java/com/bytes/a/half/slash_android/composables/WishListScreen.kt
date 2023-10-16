package com.bytes.a.half.slash_android.composables

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bytes.a.half.slash_android.Products
import com.bytes.a.half.slash_android.R
import com.bytes.a.half.slash_android.SlashAPIHelper
import com.bytes.a.half.slash_android.models.Product


@Composable
fun WishListScreen(wishListScreenParams: WishListScreenParams) {

    LaunchedEffect(Unit) {
        wishListScreenParams.showProgress.value = true
        val products = SlashAPIHelper.getWishListProducts()
        wishListScreenParams.products.clear()
        wishListScreenParams.products.addAll(products)
        wishListScreenParams.showProgress.value = false
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (wishlist, progress, noItemsFound) = createRefs()
        if (wishListScreenParams.showProgress.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(progress) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        } else {
            if (wishListScreenParams.products.isEmpty()) {
                Text(text = stringResource(id = R.string.no_items_found),
                    modifier = Modifier
                        .constrainAs(noItemsFound) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.White, textAlign = TextAlign.Center)
            } else {
                Products(modifier = Modifier
                    .constrainAs(wishlist) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxSize(),
                    products = wishListScreenParams.products,
                    onclick = { link ->
                        wishListScreenParams.onProductClick(link)
                    })
            }
        }
    }

}

data class WishListScreenParams(
    val context: Context,
    val products: MutableList<Product>,
    val showProgress: MutableState<Boolean>,
    val onProductClick: (link: String) -> Unit
)