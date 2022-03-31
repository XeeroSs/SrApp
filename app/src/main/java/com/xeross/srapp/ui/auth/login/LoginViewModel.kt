package com.xeross.srapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail

class LoginViewModel : BaseFirebaseViewModel() {
    
    
    fun login(email: String): LiveData<Array<ExceptionRegisterTypes>> {
        val mutableLiveData = MutableLiveData<Array<ExceptionRegisterTypes>>()
        
        val exceptions = arrayListOf<ExceptionRegisterTypes>()
        
        if (!email.isFormatEmail()) {
            exceptions.add(ExceptionRegisterTypes.INVALID_EMAIL)
        }
        
        if (exceptions.isEmpty()) {
            exceptions.add(ExceptionRegisterTypes.SUCCESS)
            // TODO("Login / Call db")
        }
        
        mutableLiveData.postValue(exceptions.toTypedArray())
        
        
        return mutableLiveData
    }
    
}