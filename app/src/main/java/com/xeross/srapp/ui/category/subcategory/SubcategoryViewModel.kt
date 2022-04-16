package com.xeross.srapp.ui.category.subcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.utils.Constants
import java.util.*

class SubcategoryViewModel : BaseFirebaseViewModel() {
    
    fun getSubCategoryTimes(categoryId: String, subcategoryId: String?): LiveData<ArrayList<Long>> {
        val mutableLiveData = MutableLiveData<ArrayList<Long>>()
        
        if (subcategoryId == null) {
            mutableLiveData.postValue(arrayListOf())
            return mutableLiveData
        }
        
        val uid = getUserId() ?: return mutableLiveData
        
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        timeCollection.get().addOnSuccessListener {
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
    
    fun getSubcategories(categoryId: String): LiveData<List<SubCategory>> {
        val mutableLiveData = MutableLiveData<List<SubCategory>>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        val categoryCollection = getCollection(getPathSubCollectionToString(uid, categoryId))
        categoryCollection.get().addOnSuccessListener { documents ->
            val document = documents.toObjects(SubCategory::class.java)
            mutableLiveData.postValue(document)
            return@addOnSuccessListener
        }.addOnFailureListener {
            mutableLiveData.postValue(null)
        }
        
        return mutableLiveData
    }
    
    fun saveTime(categoryId: String?, subcategoryId: String?, timeToMilliseconds: Long): LiveData<Long?> {
        val mutableLiveData = MutableLiveData<Long?>()
        
        if (categoryId == null || subcategoryId == null) {
            mutableLiveData.postValue(null)
            return mutableLiveData
        }
        
        val uid = getUserId() ?: return mutableLiveData
        
        val timeCollection = getCollection(getPathSubCollectionToString(uid, categoryId) + "/" + subcategoryId + "/" + Constants.DATABASE_COLLECTION_TIME)
        val timeDocument = timeCollection.document()
        val idTime = timeDocument.id
        
        timeDocument.set(Time(idTime, timeToMilliseconds, Date())).addOnSuccessListener {
            mutableLiveData.postValue(timeToMilliseconds)
        }.addOnFailureListener {
            mutableLiveData.postValue(null)
        }
        
        return mutableLiveData
    }
    
    private fun getPathSubCollectionToString(uid: String, categoryId: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}"
    }
    
}