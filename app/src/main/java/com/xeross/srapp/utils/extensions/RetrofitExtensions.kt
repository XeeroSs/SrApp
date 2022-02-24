package com.xeross.srapp.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitExtensions {
    
    /**
     * Use to simplify the network calls.
     * Call an observer when the response is a success.
     *
     * @param call the HTTP request, class [Call] from Retrofit library
     * @return the observer [LiveData]
     */
    fun <T> Call<T>.getHTTPRequest(): LiveData<T> {
        val data: MutableLiveData<T> = MutableLiveData<T>()
        this.enqueue(object : Callback<T> {
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