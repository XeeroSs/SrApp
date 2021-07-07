package com.xeross.srapp.helper.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkCallHelper {

    fun <T> getHTTPRequest(call: Call<T>): LiveData<T> {
        var data: MutableLiveData<T> = MutableLiveData<T>()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        data.postValue(body)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
            }
        })
        return data
    }
}