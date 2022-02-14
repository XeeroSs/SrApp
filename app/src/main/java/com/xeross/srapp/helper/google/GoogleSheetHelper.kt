package com.xeross.srapp.helper.google

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.xeross.srapp.base.BaseActivityOAuth
import com.xeross.srapp.base.BaseAsyncTask
import kotlinx.coroutines.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Callable


@Suppress("PrivatePropertyName", "LocalVariableName")
class GoogleSheetHelper(private val nameGSClass: String) {
    
    private val APPLICATION_NAME = "SrApp"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
    private val TOKENS_DIRECTORY_PATH = "tokens"
    private val ID = "1_N10ANx6O4ioiGdYhGLt2742iBGmnfCElvAMRvSQRtE"
    private var service: Sheets? = null
    private var viewModel: ViewModel? = null
    private var baseAsyncTask: BaseAsyncTask? = null
    
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)
    private val CREDENTIALS_FILE_PATH = "/credentials.json"
    
    fun build(viewModel: ViewModel, context: Context, credential: GoogleAccountCredential): GoogleSheetHelper {
        // Build a new authorized API client service.
        //  val HTTP_TRANSPORT = NetHttpTransport()
        this.viewModel = viewModel
        this.baseAsyncTask = BaseAsyncTask()
        
        return runBlocking {
            val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
            
            service = getServiceWithAuthorization(HTTP_TRANSPORT, credential)
            this@GoogleSheetHelper
        }
    }
    
    private suspend fun getServiceWithAuthorization(HTTP_TRANSPORT: NetHttpTransport, credential: GoogleAccountCredential): Sheets? {
        return coroutineScope {
            Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build()
        }
    }
    
    fun getValueToString(case: String): LiveData<String?> {
        val mutableLiveData = MutableLiveData<String?>()
        service?.let { sheets ->
            viewModel?.viewModelScope?.launch {
                val range = "$nameGSClass!$case"
                val response = sheets.spreadsheets().values()[ID, range].execute()
                val result = response.getValues()
                if (result == null || result.isEmpty()) return@launch
                val results = result[0][0].toString()
                mutableLiveData.postValue(results)
            }
        }
        return mutableLiveData
    }
    
    fun getValuesToString(rangeFrom: String, rangTo: String): LiveData<List<String>?> {
        val mutableLiveData = MutableLiveData<List<String>?>()
        service?.let { sheets ->
            viewModel?.viewModelScope?.launch {
                val range = "$nameGSClass!$rangeFrom:$rangTo"
                val response = sheets.spreadsheets().values()[ID, range].execute()
                val result = response.getValues()
                if (result == null || result.isEmpty()) return@launch
                val results = result.map { it[0].toString() }
                mutableLiveData.postValue(results)
            }
        }
        return mutableLiveData
    }
    
    fun getValuesToStringMap(baseActivityOAuth: BaseActivityOAuth?, keyRangeFrom: String, keyRangTo: String, valueRangeFrom: String, valueRangTo: String): LiveData<Map<String, String>?> {
        val mutableLiveData = MutableLiveData<Map<String, String>?>()
        baseActivityOAuth?.let { activity ->
            service?.let { sheets ->
                baseAsyncTask?.executeAsync(object : Callable<Boolean> {
                    override fun call(): Boolean {
                        try {
                            val keyRange = "$nameGSClass!$keyRangeFrom:$keyRangTo"
                            val keyResponse = sheets.spreadsheets().values()[ID, keyRange].execute()
                            val keyResult = keyResponse.getValues()
                            if (keyResult == null) {
                                mutableLiveData.postValue(null)
                                return false
                            }
                            val KeyResults = keyResult.map { it[0].toString() }
            
                            val valueRange = "$nameGSClass!$valueRangeFrom:$valueRangTo"
                            val valueResponse = sheets.spreadsheets().values()[ID, valueRange].execute()
                            val valueResult = valueResponse.getValues()
                            if (valueResult == null) {
                                mutableLiveData.postValue(null)
                                return false
                            }
                            val valueResults = valueResult.map { it[0].toString() }
            
                            mutableLiveData.postValue(KeyResults.zip(valueResults).toMap())
                        } catch (e: UserRecoverableAuthIOException) {
                            // Trigger intent to have the user authorize
                            activity.startActivityForResult(e.intent, activity.REQUEST_AUTHORIZATION)
                            return false;
                        } catch (e: IOException) {
                            e.printStackTrace()
                            return false;
                        }
                        return true
                    }
                }, object : BaseAsyncTask.Callback<Boolean> {
                    override fun onComplete(result: Boolean) {
                    }
                })
            }
        }
        return mutableLiveData
    }
    
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    @Deprecated("")
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport, context: Context): Credential {
        
        // Load client secrets.
        val resource = GoogleSheetHelper::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(resource))
        
        val tokenFolder = File(context.filesDir.toString() + File.separator + TOKENS_DIRECTORY_PATH)
        
        if (!tokenFolder.exists()) {
            tokenFolder.mkdir()
        }
        
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(FileDataStoreFactory(tokenFolder)).setAccessType("offline").build()
        
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }
    
}