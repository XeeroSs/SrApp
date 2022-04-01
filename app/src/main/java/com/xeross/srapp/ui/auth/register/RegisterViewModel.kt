package com.xeross.srapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail
import com.xeross.srapp.utils.extensions.UtilsExtensions.isValidPassword

class RegisterViewModel() : BaseFirebaseViewModel() {
    
    companion object {
        private const val MIN_PASSWORD_CHARACTERS = 8
        private const val PASSWORD_ = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}\$"
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
            when {
                it.user == null -> exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                else -> exceptions.add(ExceptionRegisterTypes.SUCCESS)
            }
            mutableLiveData.postValue(exceptions.toTypedArray())
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