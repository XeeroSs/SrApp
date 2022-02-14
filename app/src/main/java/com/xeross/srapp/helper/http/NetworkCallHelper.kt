package com.xeross.srapp.helper.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkCallHelper {
    
    /**
     * Use to simplify the network calls.
     * Call an observer when the response is a success.
     *
     * @param call the HTTP request, class [Call] from Retrofit library
     * @return the observer [LiveData]
     */
    fun <T> getHTTPRequest(call: Call<T>): LiveData<T> {
        val data: MutableLiveData<T> = MutableLiveData<T>()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.takeIf { it.isSuccessful }?.body()?.let { body ->
                    data.postValue(body)
                }
            }
    
            override fun onFailure(call: Call<T>, t: Throwable) {
            }
        })
        return data
    }
}