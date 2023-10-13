package com.bytes.a.half.slash_android.models

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val link: String,
    val website: String,
    val rating: String,
    val noOfRating: String,
    val trending: String,
    val imageUrl: String = ""
)

//val student = Gson().fromJson(json1, Product::class.java)
