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

    @GET("top-headlines?country=in&$ApiKey")
    suspend fun getNews() : Response<NewsModel>

//    @GET("top-headlines?country=in&category=business&$ApiKey")
//    suspend fun getBusinessNews() : Response<NewsModel>
//
//    @GET("top-headlines?country=in&category=International&$ApiKey")
//    suspend fun getInternationalNews() : Response<NewsModel>
//
//    @GET("top-headlines?country=in&category=medical&$ApiKey")
//    suspend fun getMedicalNews() : Response<NewsModel>
//
//    @GET("top-headlines?country=in&category=technology&$ApiKey")
//    suspend fun getTechnologyNews() : Response<NewsModel>
//
//    @GET("top-headlines?country=in&category=sports&$ApiKey")
//    suspend fun getSportsNews() : Response<NewsModel>
//
//    @GET("top-headlines?country=in&category=entertainment&$ApiKey")
//    suspend fun getEntertainmentNews() : Response<NewsModel>





}