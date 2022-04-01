package com.xeross.srapp.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFirebaseViewModel : ViewModel() {
    
    private var disposable: Disposable? = null
    private var auth: FirebaseAuth = Firebase.auth
    
    fun build() {
    
    }
    
    protected fun getDisposable(): Disposable? = disposable
    protected fun getAuth(): FirebaseAuth = auth
    
    init {
        build()
    }
    
    override fun onCleared() {
        super.onCleared()
        dispose()
    }
    
    /**
     * To call when activity is destroy/stop
     *
     */
    private fun dispose() {
        disposable?.takeIf {
            !it.isDisposed
        }?.dispose()
    }
    
    fun getDataFromCloudFirestore() {
    }
    
    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    fun isNotAuth(): Boolean {
        return true
    }
    
    fun disconnect() {
        auth.signOut()
    }
}