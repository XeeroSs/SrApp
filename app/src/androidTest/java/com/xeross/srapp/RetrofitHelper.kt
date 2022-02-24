package com.xeross.srapp

import com.xeross.srapp.helper.http.RetrofitHelper
import com.xeross.srapp.network.src.SrcApi
import com.xeross.srapp.network.src.responses.notifications.SrcNotifications
import com.xeross.srapp.network.src.responses.users.SrcUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitHelper {

    private var api: SrcApi? = null


    @JvmStatic
    fun main(args: Array<String>) {

        api = RetrofitHelper.getClient().create(SrcApi::class.java)

        api?.getNotifications()?.enqueue(object : Callback<SrcNotifications> {
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