package com.bytes.a.half.slash_android

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Search :
        BottomNavigationScreens("Search", R.string.route_search, Icons.Filled.Search)

    object Wishlist :
        BottomNavigationScreens("Wishlist", R.string.route_wishlist, Icons.Filled.Favorite)
}