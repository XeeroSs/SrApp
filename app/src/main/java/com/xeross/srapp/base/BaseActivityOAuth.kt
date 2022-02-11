package com.xeross.srapp.base

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.sheets.v4.SheetsScopes

abstract class BaseActivityOAuth : BaseActivity() {
    
    private val REQUEST_ACCOUNT_PICKER = 1
    private val REQUEST_GOOGLE_PLAY_SERVICES = 2
    val REQUEST_AUTHORIZATION = 3
    private val PREF_ACCOUNT_NAME = "name"
    private var authorization: MutableLiveData<Boolean>? = null
    
    var credential: GoogleAccountCredential? = null
    private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)
    
    private fun authorize(isAuthorized: Boolean) {
        authorization?.postValue(isAuthorized)
    }
    
    fun getAuthorization(): LiveData<Boolean>? {
        return authorization
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        this.authorization = MutableLiveData()
        
        credential = GoogleAccountCredential.usingOAuth2(this, SCOPES).also {
            val settings = getPreferences(MODE_PRIVATE)
            it.selectedAccountName = settings.getString("user", null)
        }
        
        chooseAccount()
    }
    
    private fun chooseAccount() {
        credential?.let {
            startActivityForResult(it.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GOOGLE_PLAY_SERVICES -> if (resultCode == RESULT_OK) {
                haveGooglePlayServices()
            } else {
                checkGooglePlayServicesAvailable()
            }
            REQUEST_AUTHORIZATION -> if (resultCode == RESULT_OK) {
                authorize(true)
            } else {
                chooseAccount()
            }
            REQUEST_ACCOUNT_PICKER -> if (resultCode == RESULT_OK && data != null && data.extras != null) {
                val accountName = data.extras?.getString(AccountManager.KEY_ACCOUNT_NAME)
                if (accountName != null) {
                    credential?.selectedAccountName = accountName
                    val settings = getPreferences(MODE_PRIVATE)
                    val editor = settings.edit()
                    editor.putString(PREF_ACCOUNT_NAME, accountName)
                    editor.apply()
                    authorize(true)
                }
            }
        }
    }
    
    private fun checkGooglePlayServicesAvailable(): Boolean {
        val connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
            return false
        }
        return true
    }
    
    private fun haveGooglePlayServices() {
        credential?.let {
            // check if there is already an account selected
            if (it.selectedAccountName == null) {
                // ask user to choose account
                chooseAccount()
            } else {
            }
        }
    }
    
    open fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        runOnUiThread {
            val dialog = GooglePlayServicesUtil.getErrorDialog(connectionStatusCode, this@BaseActivityOAuth, REQUEST_GOOGLE_PLAY_SERVICES)
            dialog!!.show()
        }
    }
    
}