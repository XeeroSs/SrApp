package com.xeross.srapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.User
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_USERS
import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail
import com.xeross.srapp.utils.extensions.UtilsExtensions.isValidPassword

class RegisterViewModel : BaseFirebaseViewModel() {
    
    private fun createUserToDatabase(id: String, pseudo: String): Task<Void> {
        return insertDocument(getCollection(DATABASE_COLLECTION_USERS), User(id, pseudo, null), id)
    }
    
    fun register(pseudo: String, email: String, password: String, confirmPassword: String): LiveData<Array<ExceptionRegisterTypes>> {
        val mutableLiveData = MutableLiveData<Array<ExceptionRegisterTypes>>()
        
        val exceptions = arrayListOf<ExceptionRegisterTypes>()
        
        if (!email.isFormatEmail()) exceptions.add(ExceptionRegisterTypes.INVALID_EMAIL)
        
        if (!password.isValidPassword()) {
            exceptions.add(ExceptionRegisterTypes.INVALID_PASSWORD)
        }
        if (password != confirmPassword) {
            exceptions.add(ExceptionRegisterTypes.CONFIRM_PASSWORD_IS_DIFFERENT)
        }
        
        if (exceptions.isNotEmpty()) {
            mutableLiveData.postValue(exceptions.toTypedArray())
            return mutableLiveData
        }
        
        getAuth().createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            when (it.user) {
                null -> {
                    exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                    mutableLiveData.postValue(exceptions.toTypedArray())
                }
                else -> {
                    createUserToDatabase(it.user!!.uid, pseudo).addOnSuccessListener {
                        exceptions.add(ExceptionRegisterTypes.SUCCESS)
                        mutableLiveData.postValue(exceptions.toTypedArray())
                    }.addOnFailureListener { _ ->
                        exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                        it.user?.delete()
                        mutableLiveData.postValue(exceptions.toTypedArray())
                    }
                }
            }
        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthUserCollisionException -> exceptions.add(ExceptionRegisterTypes.EMAIL_ALREADY_USE)
                else -> exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
            }
            mutableLiveData.postValue(exceptions.toTypedArray())
        }
        
        return mutableLiveData
    }
    
}