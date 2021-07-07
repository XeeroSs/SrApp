package com.xeross.srapp.helper.http

import com.xeross.srapp.model.src.users.Src
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SrcApiService {

    @GET("users/{id}")
    fun getProfile(@Path("id") name: String/*, @Query("key") api_key: String*/): Call<Src>

}