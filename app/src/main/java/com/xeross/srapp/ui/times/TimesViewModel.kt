package com.xeross.srapp.ui.times

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.utils.Constants
import java.util.*

class TimesViewModel : BaseFirebaseViewModel() {
    
    fun getTimes(categoryId: String, subcategoryId: String): LiveData<ArrayList<Time>> {
        val mutableLiveData = MutableLiveData<ArrayList<Time>>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        timeCollection.orderBy("createdAt", Query.Direction.DESCENDING).get().addOnSuccessListener {
            it.toObjects(Time::class.java).let { timeObject ->
                val arrayTimes = arrayListOf<Time>()
                timeObject.forEach { time ->
                    arrayTimes.add(time)
                }
                mutableLiveData.postValue(arrayTimes)
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(arrayListOf())
        }
        
        return mutableLiveData
    }
    
    private fun getPathSubCollectionToString(uid: String, categoryId: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}"
    }
    
    fun deleteTimes(context: LifecycleOwner, categoryId: String, subcategoryId: String, toggles: ArrayList<Time>, times: ArrayList<Time>): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        
        val uid = getUserId() ?: return mutableLiveData
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        
        val best = times.stream().min(Comparator.comparing(Time::time)).get()
        
        if (toggles.contains(best)) {
            times.removeAll(toggles)
            val newBest = times.stream().min(Comparator.comparing(Time::time)).get()
            
            val subcategoryCollection = getCollection(getPathSubCollectionToString(uid, categoryId))
            
            subcategoryCollection.document(subcategoryId).update("timeInMilliseconds", newBest.time).addOnSuccessListener {
                deleteTimes(toggles, timeCollection).observe(context, {
                    mutableLiveData.postValue(true)
                })
            }.addOnFailureListener {
                mutableLiveData.postValue(false)
            }
            
            return mutableLiveData
        }
        
        deleteTimes(toggles, timeCollection).observe(context, {
            mutableLiveData.postValue(true)
        })
        
        return mutableLiveData
    }
    
    private fun deleteTimes(toggles: ArrayList<Time>, timeCollection: CollectionReference): LiveData<Void> {
        val mutableLiveData = MutableLiveData<Void>()
        
        val iterator = toggles.iterator()
        
        while (iterator.hasNext()) {
            val time = iterator.next()
            val hasNext = iterator.hasNext()
            timeCollection.document(time.timeId).delete().addOnCompleteListener {
                if (!hasNext) mutableLiveData.postValue(null)
            }
            
        }
        
        return mutableLiveData
    }
    
    fun updateTime( categoryId: String, subcategoryId: String, timeToMilliseconds: Long, time: Time): LiveData<Long?> {
        val mutableLiveData = MutableLiveData<Long?>()
        
        val uid = getUserId() ?: return mutableLiveData
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        
        timeCollection.document(time.timeId).update("time", timeToMilliseconds).addOnSuccessListener {
            mutableLiveData.postValue(timeToMilliseconds)
        }.addOnFailureListener {
            mutableLiveData.postValue(null)
        }
        
        
        return mutableLiveData
    }
    
}