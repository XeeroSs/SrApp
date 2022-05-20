package com.xeross.srapp.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.xeross.srapp.R

object FirestoreUtils {
    
    inline fun <reified T> Task<DocumentSnapshot>.query(): LiveData<LiveDataQuery<T>> {
        val result = MutableLiveData<LiveDataQuery<T>>()
        this.addOnSuccessListener {
            
            if (!it.exists()) {
                result.postValue(LiveDataQuery(ResultLiveDataType.NOT_EXIST, R.string.it_does_not_exist, null))
                return@addOnSuccessListener
            }
            
            it.toObject(T::class.java)?.let { data ->
                result.postValue(LiveDataQuery(ResultLiveDataType.SUCCESS, R.string.success, data))
                return@addOnSuccessListener
            }
            
            throw FirebaseFirestoreException("The requested document cannot be mapped to ${T::class.java} object. Please check if the data fields match.", FirebaseFirestoreException.Code.INVALID_ARGUMENT)
        }
        this.addOnFailureListener { e ->
            e.printStackTrace()
            val message = when (e.cause) {
                is FirebaseNetworkException -> R.string.no_internet_connection_was_found
                else -> R.string.an_error_has_occured_please_try_again
            }
            result.postValue(LiveDataQuery(ResultLiveDataType.SUCCESS, message, null))
        }
        return result
    }
    
    inline fun <reified T> Task<QuerySnapshot>.queryAll(): LiveData<LiveDataQuery<List<T>>> {
        val result = MutableLiveData<LiveDataQuery<List<T>>>()
        this.addOnSuccessListener {
            
            if (it == null) {
                result.postValue(LiveDataQuery(ResultLiveDataType.NOT_EXIST, R.string.it_does_not_exist, null))
                return@addOnSuccessListener
            }
            
            try {
                val list = it.toObjects(T::class.java)
                result.postValue(LiveDataQuery(ResultLiveDataType.SUCCESS, R.string.success, list))
                return@addOnSuccessListener
            } catch (e: Exception) {
                throw FirebaseFirestoreException("The requested document cannot be mapped to ${T::class.java} object. Please check if the data fields match.", FirebaseFirestoreException.Code.INVALID_ARGUMENT)
            }
        }
        this.addOnFailureListener { e ->
            e.printStackTrace()
            val message = when (e.cause) {
                is FirebaseNetworkException -> R.string.no_internet_connection_was_found
                else -> R.string.an_error_has_occured_please_try_again
            }
            result.postValue(LiveDataQuery(ResultLiveDataType.SUCCESS, message, null))
        }
        return result
    }
    
    fun Task<Void>.post(): LiveData<LiveDataPost> {
        val result = MutableLiveData<LiveDataPost>()
        this.addOnSuccessListener {
            result.postValue(LiveDataPost(ResultLiveDataType.SUCCESS, R.string.success))
        }
        this.addOnFailureListener { e ->
            e.printStackTrace()
            val message = when (e.cause) {
                is FirebaseNetworkException -> R.string.no_internet_connection_was_found
                else -> R.string.an_error_has_occured_please_try_again
            }
            result.postValue(LiveDataPost(ResultLiveDataType.SUCCESS, message))
        }
        return result
    }
    
}