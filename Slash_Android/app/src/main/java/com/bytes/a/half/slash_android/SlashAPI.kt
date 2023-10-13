package com.bytes.a.half.slash_android

import com.bytes.a.half.slash_android.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SlashAPI {

    @GET("/search")
    suspend fun getSearch(@Query(APIConstants.PARAMETER_PRODUCT_NAME) productName: String): Response<List<Product>>
}