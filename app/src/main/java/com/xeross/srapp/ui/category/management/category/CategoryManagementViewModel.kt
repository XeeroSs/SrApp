package com.xeross.srapp.ui.category.management.category

import android.net.Uri
import androidx.lifecycle.LiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.livedata.FirestoreUtils.getImage
import com.xeross.srapp.utils.livedata.FirestoreUtils.post
import com.xeross.srapp.utils.livedata.FirestoreUtils.query
import com.xeross.srapp.utils.livedata.FirestoreUtils.uploadAndGetImage
import com.xeross.srapp.utils.livedata.LiveDataPost
import com.xeross.srapp.utils.livedata.LiveDataQuery

class CategoryManagementViewModel : BaseFirebaseViewModel() {
    
    fun getCategory(categoryId: String): LiveData<LiveDataQuery<Category>>? {
        val uid = getUserId() ?: return null
        
        val categoryCollection = getCollection(getPathCategoryToString(uid))
        
        return getDocument(categoryCollection, categoryId).query()
    }
    
    fun getCategoryImage(categoryId: String): LiveData<LiveDataQuery<Uri>>? {
        val uid = getUserId() ?: return null
        
        return getImage(getStorage(), uid, categoryId)
    }
    
    private fun getPathCategoryToString(uid: String): String {
        return "${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}"
    }
    
    fun uploadImage(categoryId: String, uri: Uri): LiveData<LiveDataQuery<Uri?>>? {
        val uid = getUserId() ?: return null
        
        return uploadAndGetImage(getStorage(), uri, uid, categoryId)
    }
    
    fun updateImagePathToDatabase(categoryId: String, idPathImage: String): LiveData<LiveDataPost>? {
        val uid = getUserId() ?: return null
        
        val categoryCollection = getCollection(getPathCategoryToString(uid))
        
        return updateDocument(categoryCollection, categoryId, Category::imageURL.name, "$uid-$idPathImage").post()
    }
    
}