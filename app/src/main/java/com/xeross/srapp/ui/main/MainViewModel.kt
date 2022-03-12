package com.xeross.srapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xeross.srapp.R
import com.xeross.srapp.network.http.RetrofitHelper
import com.xeross.srapp.network.src.SrcApi
import com.xeross.srapp.data.models.Game
import com.xeross.srapp.data.models.types.SpeedrunType

class MainViewModel : ViewModel() {
    
    companion object {
        private const val NAME_SPEED_RUNNER = "XeroSs"
        private const val NAME_GAME = "Celeste"
        private const val CATEGORY_ID = "7kjpl1gk"
    }
    
    private var api: SrcApi? = null
    private var networkCallHelper = NetworkRequestHelper()
    
    fun build() {
        api = RetrofitHelper.getClient().create(SrcApi::class.java)
    }
    
    fun getCeleste(context: Context): LiveData<Game> {
        val celeste = MutableLiveData<Game>()
        api?.let {
            networkCallHelper.getHTTPRequest(it.getPBByGame(NAME_SPEED_RUNNER, NAME_GAME)).observe((context as AppCompatActivity), { data ->
                if (data.data == null || data.data.isEmpty()) return@observe
                data.data.filter { runs -> runs.run.category == CATEGORY_ID }.singleOrNull { run ->
                    celeste.postValue(Game(SpeedrunType.CELESTE, "Celeste", R.drawable.im_celeste_level_6, run.place))
                    return@observe
                }
            })
        }
        return celeste
    }
}