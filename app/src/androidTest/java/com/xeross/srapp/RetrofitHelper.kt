package com.xeross.srapp

import com.xeross.srapp.helper.http.RetrofitHelper
import com.xeross.srapp.helper.http.SrcApiService
import com.xeross.srapp.model.src.notifications.SrcNotifications
import com.xeross.srapp.model.src.users.SrcUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitHelper {

    private var api: SrcApiService? = null


    @JvmStatic
    fun main(args: Array<String>) {

        api = RetrofitHelper.getClient().create(SrcApiService::class.java)

        api?.getNotifications(/*"1xcf0mqyj4yhzz78iuakxd3gd"*/)?.enqueue(object : Callback<SrcNotifications> {
            override fun onResponse(call: Call<SrcNotifications>, response: Response<SrcNotifications>) {


                println(response.body())
                if (response.isSuccessful) {

                    println(response.body())

                    return
                }

                println("Failure is not successful")
                return
            }

            override fun onFailure(call: Call<SrcNotifications>, t: Throwable) {
                println("Failure retrofit")
                return
            }


        })

        api?.getProfile("XeroSs")?.enqueue(object : Callback<SrcUser> {
            override fun onResponse(call: Call<SrcUser>, response: Response<SrcUser>) {


                println(response.body())
                if (response.isSuccessful) {

                    println(response.body())

                    return
                }

                println("Failure is not successful")
                return
            }

            override fun onFailure(call: Call<SrcUser>, t: Throwable) {
                println("Failure retrofit")
                return
            }


        })
    }
}