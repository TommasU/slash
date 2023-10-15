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

@Composable
fun SlashNavigationConfiguration(
    paddingValues: PaddingValues,
    navController: NavHostController,
    searchScreenParams: SearchScreenParams
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
//            WishlistScreen()
        }
    }
}