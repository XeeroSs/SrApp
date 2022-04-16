package com.xeross.srapp.ui.categoryform.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_CATEGORIES
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_SUBCATEGORIES
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_USERS_CATEGORIES
import java.util.*

class CategoryFormViewModel : BaseFirebaseViewModel() {
    
    
    // categories (Collection) -> uid (Document) -> categories (Collection) -> category (Documents) -> subcategories (Collection) -> subcategory (Documents)
    fun createCategoryToDatabase(nameCategory: String, imageCategory: String?, nameSubcategory: String, imageSubcategory: String?): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        val categoryCollection = getCollection("$DATABASE_COLLECTION_CATEGORIES/$uid/$DATABASE_COLLECTION_USERS_CATEGORIES")
        
        val idCategory = categoryCollection.document().id
        val category = Category(idCategory, nameCategory, imageCategory)
        
        insertDocument(categoryCollection, category, idCategory).addOnSuccessListener {
            
            val subCategoryCollection = categoryCollection.document(idCategory).collection(DATABASE_COLLECTION_SUBCATEGORIES)
            val idSubCategory = subCategoryCollection.document().id
            
            val subCategory = SubCategory(idSubCategory, 0, nameSubcategory, imageSubcategory, 0L)
            
            subCategoryCollection.document(idSubCategory).set(subCategory).addOnSuccessListener {
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