package com.xeross.srapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.SubCategory
import java.util.*

class GameDetailViewModel : BaseFirebaseViewModel() {
    
    fun getSubCategoryTimes(subcategoryId: String): LiveData<List<Date>> {
        val mutableLiveData = MutableLiveData<List<Date>>()
        
        return mutableLiveData
    }
    
    fun getSubcategories(categoryId: String): LiveData<List<SubCategory>> {
        val mutableLiveData = MutableLiveData<List<SubCategory>>()
        
        return mutableLiveData
    }
    
}