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
    val image_url: String = ""
)
