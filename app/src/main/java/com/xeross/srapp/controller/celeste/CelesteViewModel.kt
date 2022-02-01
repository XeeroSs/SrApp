package com.xeross.srapp.controller.celeste

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.xeross.srapp.base.BaseActivityOAuth
import com.xeross.srapp.helper.google.CelesteSheetsHelper
import com.xeross.srapp.model.LeaderBoard
import java.lang.ref.WeakReference

class CelesteViewModel(private val context: WeakReference<Context>) : ViewModel() {
    
    
    private var nSheets = ""
    private var celesteSheetHelper: CelesteSheetsHelper? = null
    
    fun build(nSheets: String, credential: GoogleAccountCredential, baseActivityOAuth: BaseActivityOAuth) {
        this.nSheets = nSheets
        celesteSheetHelper = context.get()?.let { CelesteSheetsHelper(this, nSheets, it, credential).build(baseActivityOAuth) }
    }
    
    fun getCelesteSheets() = celesteSheetHelper
    
    fun getAnyWRLeaderBoard(): LiveData<ArrayList<LeaderBoard>> {
        val leaderBoards = MutableLiveData<ArrayList<LeaderBoard>>()
        getCelesteSheets()?.let { helper ->
            
            (context.get() as? LifecycleOwner)?.let { context ->
                
                getSRNameAnyPercentRunsWordRecord(helper, context).observe(context, { result ->
                    if (result == null) return@observe
    
                    val data = ArrayList<LeaderBoard>()
    
                    for (i in result.entries.indices) {
                        val place = result.values.toList()[0]
                        val name = result.values.toList()[0]
                        val time = result.values.toList()[0]
                        val differenceInTime = result.values.toList()[0]
                        data.add(LeaderBoard(name, place, time, differenceInTime))
                    }
    
                    leaderBoards.postValue(data)
                })
            }
            
        }
        return leaderBoards
    }
    
    private fun getSRNameAnyPercentRunsWordRecord(helper: CelesteSheetsHelper, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        helper.fetchAnyPercentRunsWordRecord().observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
}