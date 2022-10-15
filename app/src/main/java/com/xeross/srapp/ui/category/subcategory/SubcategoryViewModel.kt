package com.xeross.srapp.ui.category.subcategory

import androidx.lifecycle.LiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.data.models.User
import com.xeross.srapp.ui.category.subcategory.types.TimeSortType
import com.xeross.srapp.ui.settings.types.SettingType
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.livedata.FirestoreUtils.post
import com.xeross.srapp.utils.livedata.FirestoreUtils.query
import com.xeross.srapp.utils.livedata.FirestoreUtils.queryAll
import com.xeross.srapp.utils.livedata.LiveDataPost
import com.xeross.srapp.utils.livedata.LiveDataQuery

class SubcategoryViewModel : BaseFirebaseViewModel() {
    
    fun getSubCategoryTimes(categoryId: String, subcategoryId: String?, type: TimeSortType): LiveData<LiveDataQuery<List<Time>>>? {
        if (subcategoryId == null) return null
        
        val uid = getUserId() ?: return null
        
        val timeCollection = getCollectionPath(getPathSubCollectionToString(uid, categoryId), subcategoryId, Constants.DATABASE_COLLECTION_TIME)
        
        return getDocumentByTimestamp(timeCollection, type.timeToDays).queryAll()
    }
    
    fun getBestOnAllRuns(categoryId: String, subcategoryId: String): LiveData<LiveDataQuery<SubCategory>>? {
        val uid = getUserId() ?: return null
        
        val collection = getCollection(getPathSubCollectionToString(uid, categoryId))
        
        return getDocument(collection, subcategoryId).query()
    }
    
    fun saveTimeIfBestToSubcategoryDocument(categoryId: String, subcategoryId: String, timeToMilliseconds: Long): LiveData<LiveDataPost>? {
        val uid = getUserId() ?: return null
        val path = getPathSubCollectionToString(uid, categoryId)
        val timeCollection = getCollection(path)
        return updateDocument(timeCollection, subcategoryId, SubCategory::timeInMilliseconds.name, timeToMilliseconds).post()
    }
    
    
    fun saveTime(categoryId: String, subcategoryId: String, timeToMilliseconds: Long): LiveData<LiveDataPost>? {
        val uid = getUserId() ?: return null
        val path = getPathSubCollectionToString(uid, categoryId)
        
        val timeCollection = getCollectionPath(path, subcategoryId, Constants.DATABASE_COLLECTION_TIME)
        val timeDocument = timeCollection.document()
        val idTime = timeDocument.id
        
        return insertDocument(timeCollection, Time(idTime, timeToMilliseconds), idTime).post()
    }
    
    fun updateUserProfile(isBest: Boolean): LiveData<LiveDataPost>? {
        
        val uid = getUserId() ?: return null
        val map = HashMap<String, Any>()
        val increment = FieldValue.increment(1)
        
        map[User::totalTimes.name] = increment
        
        if (isBest) {
            map[User::totalBest.name] = increment
            println(User::lastBestAt.name)
            map[User::lastBestAt.name] = Timestamp.now()
        }
        
        return updateDocument(getCollection(Constants.DATABASE_COLLECTION_USERS), uid, map).post()
    }
    
    private fun getPathSubCollectionToString(uid: String, categoryId: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}"
    }
    
    fun getToggleFromSharedPreferences(settingType: SettingType): Boolean {
        return getSharedPreferences(settingType)
    }
    
    fun updateCategoryLastUpdatedAt(categoryId: String): LiveData<LiveDataPost>? {
        val uid = getUserId() ?: return null
        
        val timeCollection = getCollectionPath("${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}")
        
        return updateDocument(timeCollection, categoryId, Category::lastUpdatedAt.name, Timestamp.now()).post()
    }
    
}