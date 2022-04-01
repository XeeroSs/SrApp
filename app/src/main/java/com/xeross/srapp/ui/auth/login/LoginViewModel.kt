package com.xeross.srapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail

class LoginViewModel : BaseFirebaseViewModel() {
    
    
    fun login(email: String, password: String): LiveData<Array<ExceptionRegisterTypes>> {
        val mutableLiveData = MutableLiveData<Array<ExceptionRegisterTypes>>()
        
        val exceptions = arrayListOf<ExceptionRegisterTypes>()
        
        if (!email.isFormatEmail()) {
            exceptions.add(ExceptionRegisterTypes.INVALID_EMAIL)
            mutableLiveData.postValue(exceptions.toTypedArray())
            return mutableLiveData
        }
        
        if (exceptions.isEmpty()) {
            getAuth().signInWithEmailAndPassword(email, password).addOnSuccessListener {
                when {
                    it.user == null -> exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                    else -> exceptions.add(ExceptionRegisterTypes.SUCCESS)
                }
                mutableLiveData.postValue(exceptions.toTypedArray())
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidUserException -> exceptions.add(ExceptionRegisterTypes.EMAIL_OR_PASSWORD_INCORRECT)
                    else -> exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                }
                mutableLiveData.postValue(exceptions.toTypedArray())
            }
        }
        
        
        return mutableLiveData
    }
    
}