package com.xeross.srapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.User
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.Utils.toPercent
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormat

class ProfileViewModel : BaseFirebaseViewModel() {
    
    fun editPseudo(user: User) {
    
    }
    
    fun editProfileImage(user: User) {
    
    }
    
    fun getPseudo(user: User?): String {
        return user?.pseudo ?: "???"
    }
    
    fun getProfileImage(user: User?): String? {
        return user?.profileImageURL
    }
    
    fun getObjectUser(): LiveData<User?> {
        val mutableLiveData = MutableLiveData<User?>()
        
        val uid = getUserId() ?: return mutableLiveData
        
        getDocument(getCollection(Constants.DATABASE_COLLECTION_USERS), uid).addOnSuccessListener {
            
            it.toObject(User::class.java)?.let { user ->
                mutableLiveData.postValue(user)
                return@addOnSuccessListener
            }
            
            mutableLiveData.postValue(null)
        }.addOnFailureListener {
            mutableLiveData.postValue(null)
        }
        
        return mutableLiveData
    }
    
    fun getAccountCreatedAt(user: User): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        
        val value = user.createdAt.toDate().toFormat()
        
        mutableLiveData.postValue(value)
        
        return mutableLiveData
    }
    
    fun getLastBest(user: User): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        
        val value = user.lastBestAt.toDate().toFormat()
        
        mutableLiveData.postValue(value)
        
        return mutableLiveData
    }
    
    fun getTotalBest(user: User): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        
        val value = user.totalBest.toString()
        
        mutableLiveData.postValue(value)
        
        return mutableLiveData
    }
    
    fun getBestRate(user: User): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        
        val times = user.totalTimes
        val best = user.totalBest
        
        val value = toPercent(times, best).toString() + "%"
        
        mutableLiveData.postValue(value)
        
        return mutableLiveData
    }
    
    fun getTotalTimes(user: User): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        
        val value = user.totalTimes.toString()
        
        mutableLiveData.postValue(value)
        
        return mutableLiveData
    }
}