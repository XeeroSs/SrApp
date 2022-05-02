package com.xeross.srapp.ui.category.subcategory

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.ui.category.subcategory.types.TimeSortType
import com.xeross.srapp.ui.settings.types.SettingType
import com.xeross.srapp.utils.Constants
import java.util.*

class SubcategoryViewModel : BaseFirebaseViewModel() {
    
    fun getSubCategoryTimes(categoryId: String, subcategoryId: String?, type: TimeSortType): LiveData<ArrayList<Long>> {
        val mutableLiveData = MutableLiveData<ArrayList<Long>>()
        
        if (subcategoryId == null) {
            mutableLiveData.postValue(arrayListOf())
            return mutableLiveData
        }
        
        val uid = getUserId() ?: return mutableLiveData
        
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        
        getDocumentByTimestamp(timeCollection, type.timeToDays).addOnSuccessListener {
            it.toObjects(Time::class.java).let { timeObject ->
                val arrayTimes = arrayListOf<Long>()
                timeObject.forEach { time ->
                    arrayTimes.add(time.time)
                }
                mutableLiveData.postValue(arrayTimes)
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(arrayListOf())
        }
        
        return mutableLiveData
    }
    
    fun getBestOnAllRuns(categoryId: String, subcategoryId: String): LiveData<Long?> {
        val mutableLiveData = MutableLiveData<Long?>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        val collection = getCollection(getPathSubCollectionToString(uid, categoryId))
        
        collection.document(subcategoryId).get().addOnSuccessListener {
            it.toObject(SubCategory::class.java)?.let { timeObject ->
                mutableLiveData.postValue(timeObject.timeInMilliseconds)
                return@addOnSuccessListener
            }
            mutableLiveData.postValue(null)
        }.addOnFailureListener {
            mutableLiveData.postValue(null)
        }
        
        return mutableLiveData
    }
    
    private fun saveTimeIfBestToSubcategoryDocument(subcategoryId: String, timeToMilliseconds: Long, path: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        
        val timeCollection = getCollection(path)
        
        timeCollection.document(subcategoryId).update("timeInMilliseconds", timeToMilliseconds).addOnSuccessListener {
            mutableLiveData.postValue(true)
        }.addOnFailureListener {
            mutableLiveData.postValue(false)
        }
        
        return mutableLiveData
    }
    
    private fun saveTimeToTimesCollection(subcategoryId: String, timeToMilliseconds: Long, path: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        
        val timeCollection = getCollection(path + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        val timeDocument = timeCollection.document()
        val idTime = timeDocument.id
        
        timeDocument.set(Time(idTime, timeToMilliseconds)).addOnSuccessListener {
            mutableLiveData.postValue(true)
        }.addOnFailureListener {
            mutableLiveData.postValue(false)
        }
        
        return mutableLiveData
    }
    
    fun saveTime(context: LifecycleOwner, categoryId: String?, subcategoryId: String?, timeToMilliseconds: Long, isBest: Boolean): LiveData<Long?> {
        val mutableLiveData = MutableLiveData<Long?>()
        
        if (categoryId == null || subcategoryId == null) {
            mutableLiveData.postValue(null)
            return mutableLiveData
        }
        
        val uid = getUserId() ?: return mutableLiveData
        val path = getPathSubCollectionToString(uid, categoryId)
        
        saveTimeToTimesCollection(subcategoryId, timeToMilliseconds, path).observe(context, {
            if (!it) {
                mutableLiveData.postValue(null)
                return@observe
            }
    
            if (isBest) {
                saveTimeIfBestToSubcategoryDocument(subcategoryId, timeToMilliseconds, path).observe(context, {
                    mutableLiveData.postValue(timeToMilliseconds)
                })
                return@observe
            }
    
            mutableLiveData.postValue(timeToMilliseconds)
            return@observe
        })
        return mutableLiveData
    }
    
    private fun getPathSubCollectionToString(uid: String, categoryId: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}"
    }
    
    fun getToggleFromSharedPreferences(settingType: SettingType): Boolean {
        return getSharedPreferences(settingType)
    }
    
}