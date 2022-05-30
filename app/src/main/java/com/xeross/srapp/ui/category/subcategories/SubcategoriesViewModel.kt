package com.xeross.srapp.ui.category.subcategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.livedata.FirestoreUtils.queryAll
import com.xeross.srapp.utils.livedata.LiveDataQuery

class SubcategoriesViewModel : BaseFirebaseViewModel() {
    
    fun getSubcategories(categoryId: String): LiveData<ArrayList<SubCategory>> {
        val mutableLiveData = MutableLiveData<ArrayList<SubCategory>>()
        
        val subcategories = arrayListOf<SubCategory>()
        
        val uid = getUserId() ?: run {
            mutableLiveData.postValue(subcategories)
            return mutableLiveData
        }
        
        val categoryCollection = getCollection(getPathSubCollectionToString(uid, categoryId))
        
        categoryCollection.orderBy("createdAt", Query.Direction.ASCENDING).get().addOnSuccessListener {
            
            it?.takeIf { !it.isEmpty }?.toObjects(SubCategory::class.java)?.forEach { sc ->
                subcategories.add(sc)
            }
            
            mutableLiveData.postValue(subcategories)
        }.addOnFailureListener {
            mutableLiveData.postValue(subcategories)
        }
        return mutableLiveData
    }
    
    private fun addSubcategory(categoryId: String) {
    
    }
    
    private fun getPathSubCollectionToString(uid: String, categoryId: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}"
    }
    
    fun getTimeWithLimit(categoryId: String, subcategoryId: String, limit: Long): LiveData<LiveDataQuery<List<Time>>>? {
        val uid = getUserId() ?: return null
        
        val timeCollection = getCollectionPath(getPathSubCollectionToString(uid, categoryId), subcategoryId, Constants.DATABASE_COLLECTION_TIME)
        
        return getDocumentByLimit(timeCollection, limit).queryAll()
    }
}