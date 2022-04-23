package com.xeross.srapp.ui.categoryform.subcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.utils.Constants

class SubcategoryFormViewModel : BaseFirebaseViewModel() {
    
    
    // categories (Collection) -> uid (Document) -> categories (Collection) -> category (Documents) -> subcategories (Collection) -> subcategory (Documents)
    fun createSubcategoryToDatabase(categoryId: String, nameSubcategory: String, imageSubcategory: String?): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        val subcategoryCollection = getCollection("${Constants.DATABASE_COLLECTION_CATEGORIES}/$uid/${Constants.DATABASE_COLLECTION_USERS_CATEGORIES}/$categoryId/${Constants.DATABASE_COLLECTION_SUBCATEGORIES}")
        
        val positionDocumentRequest = subcategoryCollection.orderBy("position", Query.Direction.DESCENDING).limit(1)
        
        positionDocumentRequest.get().addOnSuccessListener {
            
            it.takeIf { !it.isEmpty }?.toObjects(SubCategory::class.java)?.let { positionDocument ->
                val position = (positionDocument[0].position + 1)
                
                val idSubcategory = subcategoryCollection.document().id
                val subcategory = SubCategory(idSubcategory, position, nameSubcategory, imageSubcategory, 0L)
                
                insertDocument(subcategoryCollection, subcategory, idSubcategory).addOnSuccessListener {
                    
                    mutableLiveData.postValue(true)
                    
                }.addOnFailureListener {
                    mutableLiveData.postValue(false)
                }
                return@addOnSuccessListener
            }
            
            val position = 0
            
            val idSubcategory = subcategoryCollection.document().id
            val subcategory = SubCategory(idSubcategory, position, nameSubcategory, imageSubcategory, 0L)
            
            insertDocument(subcategoryCollection, subcategory, idSubcategory).addOnSuccessListener {
                
                mutableLiveData.postValue(true)
                
            }.addOnFailureListener {
                mutableLiveData.postValue(false)
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(false)
        }
        
        return mutableLiveData
    }
}