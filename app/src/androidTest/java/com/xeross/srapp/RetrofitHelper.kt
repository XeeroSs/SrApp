package com.xeross.srapp

import com.xeross.srapp.helper.http.RetrofitHelper
import com.xeross.srapp.helper.http.SrcApiService
import com.xeross.srapp.model.src.users.Src
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitHelper {

    private var api: SrcApiService? = null


    @JvmStatic
    fun main(args: Array<String>) {

        api = RetrofitHelper.getClient().create(SrcApiService::class.java)

        api?.getProfile("XeroSs")?.enqueue(object : Callback<Src> {
            override fun onResponse(call: Call<Src>, response: Response<Src>) {


                println(response.body())
                if (response.isSuccessful) {

                    println(response.body())

                    return
                }

                println("Failure is not successful")
                return
            }

            override fun onFailure(call: Call<Src>, t: Throwable) {
                println("Failure retrofit")
                return
            }


        })
    }
}