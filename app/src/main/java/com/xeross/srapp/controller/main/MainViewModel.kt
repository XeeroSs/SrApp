package com.xeross.srapp.controller.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xeross.srapp.R
import com.xeross.srapp.helper.http.NetworkCallHelper
import com.xeross.srapp.helper.http.RetrofitHelper
import com.xeross.srapp.helper.http.SrcApiService
import com.xeross.srapp.model.Game

class MainViewModel(private val context: Context) : ViewModel() {

    private var api: SrcApiService? = null
    private var networkCallHelper = NetworkCallHelper()

    @Suppress("SpellCheckingInspection")
    private val nameSpeedrunner = "XeroSs"

    @Suppress("SpellCheckingInspection")
    private val nameSpeedrun = "Celeste"

    fun build() {
        api = RetrofitHelper.getClient().create(SrcApiService::class.java)
    }

    fun getCeleste(): LiveData<Game> {
        val celeste = MutableLiveData<Game>()
        api?.let {
            networkCallHelper.getHTTPRequest(it.getPBByGame(nameSpeedrunner, nameSpeedrun)).observe((context as AppCompatActivity), { data ->
                if (data.data == null || data.data.isEmpty()) return@observe
                celeste.postValue(Game("celeste", "Celeste", R.drawable.im_celeste, data.data[0].place))
            })
        }
        return celeste
    }
}