package com.bytes.a.half.slash_android

import com.bytes.a.half.slash_android.APIConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
        httpClientBuilder.connectTimeout(60,TimeUnit.SECONDS)
        val httpClient = httpClientBuilder.build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient)
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}