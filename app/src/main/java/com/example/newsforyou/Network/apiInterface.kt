package com.example.newsforyou.Network

import com.example.newsforyou.Model.NewsModel
import com.example.newsforyou.Utils.Constants.ApiKey
import com.example.newsforyou.Utils.Constants.BaseUrl
import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface apiInterface {

    companion object {
        operator fun invoke(): apiInterface {
            return  Retrofit.Builder().baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(apiInterface::class.java)
        }
    }

    @GET("top-headlines?country=us&category=sports&$ApiKey")
    suspend fun getNews() : Response<NewsModel>
}