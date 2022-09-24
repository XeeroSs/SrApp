package com.xeross.srapp.base

import android.content.Context
import android.content.SharedPreferences
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
import com.xeross.srapp.listener.ISharedPreferences
import io.reactivex.rxjava3.disposables.Disposable
import java.sql.Timestamp
import java.util.*

abstract class BaseFirebaseViewModel : ViewModel() {
    
    private var disposable: Disposable? = null
    private var auth: FirebaseAuth = Firebase.auth
    private var database = Firebase.firestore
    
    protected var sharedPreferences: SharedPreferences? = null
    
    private lateinit var currentAt: Date
    
    fun build() {
        currentAt = Date()
    }
    
    fun buildSharedPreferences(context: Context, key: String) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
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
    
    // CRUD - Update
    protected fun updateDocument(collectionReference: CollectionReference, documentPath: String, field: String, value: Any): Task<Void> {
        return collectionReference.document(documentPath).update(field, value)
    }
    
    // CRUD - Delete
    protected fun deleteDocument(collectionReference: CollectionReference, documentPath: String): Task<Void> {
        return collectionReference.document(documentPath).delete()
    }
    
    protected fun <T> applySharedPreferences(iSharedPreferences: ISharedPreferences<T>, t: T) {
        sharedPreferences?.let { sp ->
            with(sp.edit() ?: return) {
                when (t) {
                    is Boolean -> putBoolean(iSharedPreferences.getKey(), t)
                    is String -> putString(iSharedPreferences.getKey(), t)
                    is Int -> putInt(iSharedPreferences.getKey(), t)
                    else -> throw ClassCastException("String / Int / Boolean")
                }
                apply()
            }
        } ?: throw Exception("must call buildSharedPreferences() for init")
    }
    
    protected inline fun <reified T> getSharedPreferences(iSharedPreferences: ISharedPreferences<T>): T {
        with(sharedPreferences) {
            if (this == null) throw Exception("must call buildSharedPreferences()")
            val data = iSharedPreferences.getDefaultValue()
            return when (data) {
                is Boolean -> getBoolean(iSharedPreferences.getKey(), data)
                is String -> getString(iSharedPreferences.getKey(), data)
                is Int -> getInt(iSharedPreferences.getKey(), data)
                else -> throw ClassCastException("String / Int / Boolean")
            } as T
        }
    }
    
    protected fun getCollectionPath(vararg values: String): CollectionReference {
        val path = StringBuilder().apply {
            val size = values.size
            for (i in 0 until size) {
                val path = values[i]
                if (i != 0) append("/")
                append(path)
            }
        }.toString()
        return getCollection(path)
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
    
    protected fun getDocumentByLimit(collectionReference: CollectionReference, limit: Long): Task<QuerySnapshot> {
        return collectionReference.limit(limit).get()
    }
    
    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    fun isNotAuth(): Boolean {
        return true
    }
    
    fun disconnectFromFirebase() {
        auth.signOut()
    }
}