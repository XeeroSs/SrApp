package com.xeross.srapp.helper.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getClient() = Retrofit.Builder().baseUrl("https://www.speedrun.com/api/v1/").addConverterFactory(GsonConverterFactory.create()).build()

}