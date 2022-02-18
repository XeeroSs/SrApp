package com.xeross.srapp.controller.celeste

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.xeross.srapp.base.BaseActivityOAuth
import com.xeross.srapp.helper.google.CelesteSheetsRepository
import com.xeross.srapp.model.LeaderBoard
import com.xeross.srapp.model.src.users.SrcUser
import java.lang.ref.WeakReference

class CelesteViewModel(private val context: WeakReference<Context>) : ViewModel() {
    
    // To call before to make network request
    private val cacheSrcRunners = HashMap<String, SrcUser>()
    private val cacheSheetRunners = HashMap<String, ArrayList<LeaderBoard>>()
    
    private fun addToCacheRunnerFromSRC(runnerId: String, srcUser: SrcUser) {
        cacheSrcRunners[runnerId] = srcUser
    }
    
    private fun addToCacheRunnersFromSheet(categoryId: String, runners: ArrayList<LeaderBoard>) {
        cacheSheetRunners[categoryId] = runners
    }
    
    
    private var nSheets = ""
    private var celesteSheetRepository: CelesteSheetsRepository? = null
    
    fun build(credential: GoogleAccountCredential, baseActivityOAuth: BaseActivityOAuth) {
        this.nSheets = nSheets
        celesteSheetRepository = context.get()?.let { CelesteSheetsRepository(this, it, credential).build(baseActivityOAuth) }
    }
    
    private fun getCelesteSheets() = celesteSheetRepository
    
    private fun getSheetFromCache(nameGSClass: String): ArrayList<LeaderBoard>? {
        return cacheSheetRunners[nameGSClass]
    }
    
    fun getLeaderBoard(nameGSClass: String): LiveData<ArrayList<LeaderBoard>> {
        val liveDataLeaderBoards = MutableLiveData<ArrayList<LeaderBoard>>()
        
        getSheetFromCache(nameGSClass)?.let {
            liveDataLeaderBoards.value = it
            return liveDataLeaderBoards
        }
        
        getCelesteSheets()?.let { helper ->
            
            (context.get() as? LifecycleOwner)?.let { context ->
                
                getTimeAnyRunsWorldRecord(nameGSClass, helper, context).observe(context, { mapTimes ->
                    mapTimes?.let { times ->
                        getSRNameAnyRunsWorldRecord(nameGSClass, helper, context).observe(context, { mapNames ->
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
    
    private fun getTimeAnyRunsWorldRecord(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        repository.fetchAnyPercentRunsWordRecord(nameGSClass).observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
    private fun getSRNameAnyRunsWorldRecord(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        repository.fetchSRNameAnyPercentRunsWordRecord(nameGSClass).observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
    
}