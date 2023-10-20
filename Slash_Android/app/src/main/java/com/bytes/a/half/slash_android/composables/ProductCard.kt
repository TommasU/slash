package com.bytes.a.half.slash_android.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.bytes.a.half.slash_android.R
import com.bytes.a.half.slash_android.SlashHelper.getCompanyLogoUrl
import com.bytes.a.half.slash_android.models.Product


@Composable
fun ProductCard(
    product: Product,
    isWishlist: Boolean,
    onclick: () -> Unit,
    onAddToWishList: () -> Unit
) {

    val openDialog = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .clickable {
                onclick()
            }
            .padding(8.dp)
    ) {
        ConstraintLayout() {

            val (companyLogo, productImage, price, title, rating, addToWishlist) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(getCompanyLogoUrl(product.website)),
                contentDescription = null, // Add content description as needed
                modifier = Modifier
                    .size(75.dp)
                    .constrainAs(companyLogo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(product.image_url),
                contentDescription = null, // Add content description as needed
                modifier = Modifier
                    .height(150.dp) // Adjust the height as needed
                    .clip(shape = RoundedCornerShape(8.dp))
                    .constrainAs(productImage) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(companyLogo.bottom)
                    }
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = product.title ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(productImage.bottom)
                    }
            )
            Text(
                text = product.price.toString().trim(),
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .constrainAs(price) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom)
                    },
                fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
            Text(
                text = product.rating.toString().trim(),
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .constrainAs(rating) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(price.bottom)
                    })

            Button(onClick = {
                if (isWishlist) {
                    openDialog.value = true
                } else {
                    onAddToWishList()
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.bottom_bar_background
                ), contentColor = Color.White
            ), modifier = Modifier
                .constrainAs(addToWishlist) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(rating.bottom)
                }
                .padding(8.dp)) {
                Text(text = stringResource(id = if (isWishlist) R.string.remove_from_wishlist else R.string.add_to_wishlist))
            }

            if (openDialog.value) {

                AlertDialog(containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                    textContentColor = Color.White,
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Remove from Wishlist")
                    },
                    text = {
                        Text("Are you sure you want to remove this item from wishlist ? ")
                    },
                    confirmButton = {
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.bottom_bar_background
                            ), contentColor = Color.White
                        ),
                            onClick = {
                                openDialog.value = false
                                onAddToWishList()
                            }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.bottom_bar_background
                            ), contentColor = Color.White
                        ),
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Cancel")
                        }
                    }
                )
            }

        }
    }

}