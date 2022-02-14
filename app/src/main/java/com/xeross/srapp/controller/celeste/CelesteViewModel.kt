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

/*    fun getAnyWRLeaderBoard(): LiveData<ArrayList<LeaderBoard>> {
        val leaderBoards = MutableLiveData<ArrayList<LeaderBoard>>()
        getCelesteSheets()?.let { helper ->
            
            (context.get() as? LifecycleOwner)?.let { context ->
                
                getSRNameAnyPercentRunsWordRecord(helper, context).observe(context, { result ->
                    if (result == null) return@observe
    
                    val data = ArrayList<LeaderBoard>()
    
                    for (i in result.entries.indices) {
                        val place = result.values.toList()[i]
                        val name = result.values.toList()[i]
                        val time = result.values.toList()[i]
                        val differenceInTime = result.values.toList()[i]
                        data.add(LeaderBoard(name, place, time, differenceInTime))
                    }
    
                    leaderBoards.postValue(data)
                })
            }
            
        }
        return leaderBoards
    }*/
    
    fun getLeaderBoard(): LiveData<ArrayList<LeaderBoard>> {
        val liveDataLeaderBoards = MutableLiveData<ArrayList<LeaderBoard>>()
        getCelesteSheets()?.let { helper ->
            
            (context.get() as? LifecycleOwner)?.let { context ->
                
                getTimeAnyRunsWorldRecord(helper, context).observe(context, { mapTimes ->
                    mapTimes?.let { times ->
                        getSRNameAnyRunsWorldRecord(helper, context).observe(context, { mapNames ->
                            mapNames?.let { names ->
                
                                val leaderBoards = arrayListOf<LeaderBoard>()
                
                                val size = names.size
                
                                for (i in 1..size) {
                    
                                    val key = "#$i"
                    
                                    val name = names[key] ?: continue
                                    val time = times[key] ?: continue
                    
                                    leaderBoards.add(LeaderBoard(name, i, time))
                                }
                
                                liveDataLeaderBoards.postValue(leaderBoards)
                            }
                        })
                    }
                })
            }
            
        }
        return liveDataLeaderBoards
    }
    
    private fun getTimeAnyRunsWorldRecord(helper: CelesteSheetsHelper, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        helper.fetchAnyPercentRunsWordRecord().observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
    private fun getSRNameAnyRunsWorldRecord(helper: CelesteSheetsHelper, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        helper.fetchSRNameAnyPercentRunsWordRecord().observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
    
}