package com.bytes.a.half.slash_android.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product(
    var id: String? = null,
    val title: String? = null,
    val price: String? = null,
    val link: String? = null,
    val website: String? = null,
    val rating: String? = null,
    val noOfRating: String? = null,
    val trending: String? = null,
    val image_url: String? = null
)
