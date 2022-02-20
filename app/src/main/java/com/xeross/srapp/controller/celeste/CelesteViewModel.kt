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
import com.xeross.srapp.model.Statistic
import com.xeross.srapp.model.src.users.SrcUser
import java.lang.ref.WeakReference

class CelesteViewModel(private val context: WeakReference<Context>) : ViewModel() {
    
    // Cache
    private val cacheSrcRunners = HashMap<String, SrcUser>()
    private val cacheSheetLeaderboards = HashMap<String, ArrayList<LeaderBoard>>()
    private val cacheSheetStatistics = HashMap<String, ArrayList<Statistic>>()
    
    private val sheetRunners = ArrayList<String>()
    private val sheetStatistics = ArrayList<String>()
    
    private fun addRunnerToCacheFromSRC(runnerId: String, srcUser: SrcUser) {
        cacheSrcRunners[runnerId] = srcUser
    }
    
    private fun addStatisticToCacheFromSheet(categoryId: String, stats: ArrayList<Statistic>) {
        cacheSheetStatistics[categoryId] = stats
    }
    
    private fun addLeaderboardToCacheFromSheet(categoryId: String, runners: ArrayList<LeaderBoard>) {
        cacheSheetLeaderboards[categoryId] = runners
    }
    
    
    private var nSheets = ""
    private var celesteSheetRepository: CelesteSheetsRepository? = null
    
    fun build(credential: GoogleAccountCredential, baseActivityOAuth: BaseActivityOAuth) {
        this.nSheets = nSheets
        celesteSheetRepository = context.get()?.let { CelesteSheetsRepository(this, it, credential).build(baseActivityOAuth) }
    }
    
    private fun getCelesteSheets() = celesteSheetRepository
    
    private fun getLeaderboardFromCache(nameGSClass: String): ArrayList<LeaderBoard>? {
        return cacheSheetLeaderboards[nameGSClass]
    }
    
    private fun getStatisticFromCache(nameGSClass: String): ArrayList<Statistic>? {
        return cacheSheetStatistics[nameGSClass]
    }
    
    fun getStatistics(nameGSClass: String): LiveData<Pair<String?, ArrayList<Statistic>>> {
        val liveDataStats = MutableLiveData<Pair<String?, ArrayList<Statistic>>>()
        
        getStatisticFromCache(nameGSClass)?.let {
            liveDataStats.value = Pair(null, it)
            return liveDataStats
        }
        
        // TODO("Avoid useless requests, to update")
        if (!sheetStatistics.contains(nameGSClass)) {
            sheetStatistics.add(nameGSClass)
            
            getCelesteSheets()?.let { helper ->
                (context.get() as? LifecycleOwner)?.let { context ->
                    getBest(nameGSClass, helper, context).observe(context, { best ->
                        getWorst(nameGSClass, helper, context).observe(context, { worst ->
                            getAverage(nameGSClass, helper, context).observe(context, { average ->
                                getRunCount(nameGSClass, helper, context).observe(context, { count ->
                
                                    val stats = arrayListOf<Statistic>()
                
                                    stats.add(Statistic(best ?: "00:00:00", "Best"))
                                    stats.add(Statistic(average ?: "00:00:00", "Average"))
                                    stats.add(Statistic(worst ?: "00:00:00", "Worst"))
                                    stats.add(Statistic(count ?: "0", "Total"))
                
                                    addStatisticToCacheFromSheet(nameGSClass, stats)
                
                                    // TODO("Add screen/widget loading and stop it here")
                                    liveDataStats.postValue(Pair(nameGSClass, stats))
                                })
                            })
                        })
                    })
                }
            }
        }
        return liveDataStats
    }
    
    fun getLeaderBoard(nameGSClass: String): LiveData<Pair<String?, ArrayList<LeaderBoard>>> {
        val liveDataLeaderBoards = MutableLiveData<Pair<String?, ArrayList<LeaderBoard>>>()
        
        getLeaderboardFromCache(nameGSClass)?.let {
            liveDataLeaderBoards.value = Pair(null, it)
            return liveDataLeaderBoards
        }
        
        // TODO("Avoid useless requests, to update")
        if (!sheetRunners.contains(nameGSClass)) {
            sheetRunners.add(nameGSClass)
            
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
                
                                    addLeaderboardToCacheFromSheet(nameGSClass, leaderBoards)
                
                                    // TODO("Add screen/widget loading and stop it here")
                                    liveDataLeaderBoards.postValue(Pair(nameGSClass, leaderBoards))
                                }
                            })
                        }
                    })
                }
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
    
    private fun getBest(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<String?> {
        val best = MutableLiveData<String?>()
        
        repository.fetchBestRun(nameGSClass).observe(context, { it1 ->
            best.postValue(it1)
        })
        return best
    }
    
    private fun getWorst(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<String?> {
        val worst = MutableLiveData<String?>()
        
        repository.fetchWorstRun(nameGSClass).observe(context, { it1 ->
            worst.postValue(it1)
        })
        return worst
    }
    
    private fun getAverage(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<String?> {
        val average = MutableLiveData<String?>()
        
        repository.fetchRunsAverage(nameGSClass).observe(context, { it1 ->
            average.postValue(it1)
        })
        return average
    }
    
    private fun getRunCount(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<String?> {
        val count = MutableLiveData<String?>()
        repository.fetchRunAmount(nameGSClass).observe(context, { it1 ->
            count.postValue(it1)
        })
        return count
    }
    
    private fun getSRNameAnyRunsWorldRecord(nameGSClass: String, repository: CelesteSheetsRepository, context: LifecycleOwner): LiveData<Map<String, String>?> {
        val leaderBoards = MutableLiveData<Map<String, String>?>()
        repository.fetchSRNameAnyPercentRunsWordRecord(nameGSClass).observe(context, { it1 ->
            leaderBoards.postValue(it1)
        })
        return leaderBoards
    }
    
    
}