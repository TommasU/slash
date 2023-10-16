package com.bytes.a.half.slash_android

import com.bytes.a.half.slash_android.APIConstants.BASE_URL
import com.bytes.a.half.slash_android.models.Product
import com.bytes.a.half.slash_android.utils.FirebaseUtils
import com.google.firebase.database.ktx.snapshots
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object SlashAPIHelper {

    fun getInstance(): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Content-Type", "application/json").build()
            chain.proceed(request)
        })
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        val httpClient = httpClientBuilder.build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient)
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    fun addToWishList(product: Product) {
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val wishListReference = FirebaseUtils.database.child("wishlists").child(userId!!)
            val reference = wishListReference.push()
            product.id = reference.key
            val products: HashMap<String, Product> = HashMap()
            products[product.id!!] = product
            wishListReference.updateChildren(products as Map<String, Any>)
        }
    }

    suspend fun getWishListProducts(): ArrayList<Product> {
        val productList = ArrayList<Product>()
        val userId = FirebaseUtils.auth.uid
        if (userId.isValidString()) {
            val wishListReference = FirebaseUtils.database.child("wishlists").child(userId!!)
            val productSnapshots = wishListReference.get().await()
            for (snapshot in productSnapshots.children) {
                val product = snapshot.getValue(Product::class.java)
                if (product != null) {
                    productList.add(product)
                }
            }
        }
        return productList
    }
}