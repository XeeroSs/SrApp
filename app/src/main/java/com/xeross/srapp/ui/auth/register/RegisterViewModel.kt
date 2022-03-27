package com.xeross.srapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes

class RegisterViewModel() : BaseFirebaseViewModel() {
    
    companion object {
        private const val MIN_PASSWORD_CHARACTERS = 8
        
        // TODO("test regex with unit test")
        private const val EMAIL_FORMAT = "/^(([^<>()[\\]\\.,;:\\s@\\\"]+(\\.[^<>()[\\]\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@(([^<>()[\\]\\.,;:\\s@\\\"]+\\.)+[^<>()[\\]\\.,;:\\s@\\\"]{2,})\$/i"
    }
    
    fun register(): LiveData<Array<ExceptionRegisterTypes>> {
        val mutableLiveData = MutableLiveData<Array<ExceptionRegisterTypes>>()
        
        mutableLiveData.postValue(arrayOf(ExceptionRegisterTypes.PASSWORD_TOO_SHORT, ExceptionRegisterTypes.INVALID_PSEUDO, ExceptionRegisterTypes.INVALID_EMAIL, ExceptionRegisterTypes.CONFIRM_PASSWORD_IS_DIFFERENT))
        
        return mutableLiveData
    }
    
}