package com.xeross.srapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.User
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail

class LoginViewModel : BaseFirebaseViewModel() {
    
    private fun createUserIfNotExistToDatabase(id: String, pseudo: String): Task<Void> {
        return insertDocument(getCollection(Constants.DATABASE_COLLECTION_USERS), User(id, pseudo, null), id)
    }
    
    fun loginWithGoogle(idToken: String): LiveData<Array<ExceptionRegisterTypes>> {
        val mutableLiveData = MutableLiveData<Array<ExceptionRegisterTypes>>()
        
        val exceptions = arrayListOf<ExceptionRegisterTypes>()
        
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        getAuth().signInWithCredential(credentials).addOnSuccessListener {
            when (it.user) {
                null -> {
                    exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                    mutableLiveData.postValue(exceptions.toTypedArray())
                }
                else -> {
                    val uid = it.user!!.uid
                    getDocument(getCollection(Constants.DATABASE_COLLECTION_USERS), uid).addOnSuccessListener { document ->
                        if (!document.exists()) {
                            createUserIfNotExistToDatabase(it.user!!.uid, it.user!!.displayName ?: "").addOnSuccessListener {
                                exceptions.add(ExceptionRegisterTypes.SUCCESS)
                                mutableLiveData.postValue(exceptions.toTypedArray())
                            }.addOnFailureListener {
                                getAuth().signOut()
                                exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                                mutableLiveData.postValue(exceptions.toTypedArray())
                            }
                        } else {
                            exceptions.add(ExceptionRegisterTypes.SUCCESS)
                            mutableLiveData.postValue(exceptions.toTypedArray())
                        }
                    }.addOnFailureListener {
                        getAuth().signOut()
                        exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
                        mutableLiveData.postValue(exceptions.toTypedArray())
                    }
                }
            }
        }.addOnFailureListener {
            exceptions.add(ExceptionRegisterTypes.AUTH_FAILED)
            mutableLiveData.postValue(exceptions.toTypedArray())
        }
        
        return mutableLiveData
    }
    
    
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