package com.bytes.a.half.slash_android

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bytes.a.half.slash_android.composables.SearchScreen
import com.bytes.a.half.slash_android.composables.SearchScreenParams
import com.bytes.a.half.slash_android.composables.WishListScreen
import com.bytes.a.half.slash_android.composables.WishListScreenParams
import com.bytes.a.half.slash_android.models.Product

@Composable
fun SlashNavigationConfiguration(
    paddingValues: PaddingValues,
    navController: NavHostController,
    searchScreenParams: SearchScreenParams,
    wishListScreenParams: WishListScreenParams
) {
    NavHost(
        navController,
        startDestination = BottomNavigationScreens.Search.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomNavigationScreens.Search.route) {
            SearchScreen(searchScreenParams)
        }
        composable(BottomNavigationScreens.Wishlist.route) {
            WishListScreen(wishListScreenParams)
        }
    }
}