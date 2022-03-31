package com.xeross.srapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        
        if (exceptions.isEmpty()) {
            exceptions.add(ExceptionRegisterTypes.SUCCESS)
            // TODO("Register / Call db")
        }
        
        mutableLiveData.postValue(exceptions.toTypedArray())
        
        
        return mutableLiveData
    }
    
}