package com.bytes.a.half.slash_android.models

data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val link: String,
    val website: String,
    val rating: Double,
    val noOfRating: Int,
    val trending: String
)
