package com.example.newsforyou.Network

import com.example.newsforyou.Model.NewsModel
import com.example.newsforyou.Utils.Constants.ApiKey
import com.example.newsforyou.Utils.Constants.BaseUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface apiInterface {

    companion object {
        operator fun invoke(): apiInterface {
            return  Retrofit.Builder().baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(apiInterface::class.java)
        }
    }

    @GET("top-headlines?country=in&category=sports&$ApiKey")
    suspend fun getNews() : Response<NewsModel>
}