package com.xeross.srapp.base

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.disposables.Disposable
import java.sql.Timestamp
import java.util.*

abstract class BaseFirebaseViewModel : ViewModel() {
    
    private var disposable: Disposable? = null
    private var auth: FirebaseAuth = Firebase.auth
    private var database = Firebase.firestore
    
    private lateinit var currentAt: Date
    
    fun build() {
        currentAt = Date()
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
    
    fun getUserId(): String? {
        return getUser()?.uid
    }
    
    protected fun getCollection(collection: String): CollectionReference {
        return database.collection(collection)
    }
    
    // CRUD - Create
    protected fun insertDocument(collectionReference: CollectionReference, value: Any, idDocument: String): Task<Void> {
        return collectionReference.document(idDocument).set(value)
    }
    
    // CRUD - Read
    protected fun getDocument(collectionReference: CollectionReference, documentPath: String): Task<DocumentSnapshot> {
        return collectionReference.document(documentPath).get()
    }
    
    // CRUD - Read
    protected fun getDocument(collectionReference: CollectionReference): DocumentReference {
        return collectionReference.document()
    }
    
    // CRUD - Update
    protected fun updateDocument(collectionReference: CollectionReference, documentPath: String, map: Map<String, Any>): Task<Void> {
        return collectionReference.document(documentPath).update(map)
    }
    
    // CRUD - Delete
    protected fun deleteDocument(collectionReference: CollectionReference, documentPath: String): Task<Void> {
        return collectionReference.document(documentPath).delete()
    }
    
    protected fun getDocumentByTimestamp(collectionReference: CollectionReference, timeToInDays: Int): Task<QuerySnapshot> {
        
        if (timeToInDays <= 0) return collectionReference.get()
        
        val calendar = Calendar.getInstance()
        calendar.time = currentAt
        calendar.add(Calendar.DATE, -timeToInDays)
        val out = calendar.time
        
        val ts = Timestamp(out.time)
        
        return collectionReference.whereGreaterThan("createdAt", ts).get()
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