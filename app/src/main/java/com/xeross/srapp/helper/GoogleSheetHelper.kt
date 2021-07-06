package com.xeross.srapp.helper

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

@Suppress("PrivatePropertyName", "LocalVariableName")
class GoogleSheetHelper(private val nameGSClass: String) {

    private val APPLICATION_NAME = "SrApp"
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private val TOKENS_DIRECTORY_PATH = "tokens"
    private val ID = "1_N10ANx6O4ioiGdYhGLt2742iBGmnfCElvAMRvSQRtE"
    private var service: Sheets? = null

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)
    private val CREDENTIALS_FILE_PATH = "/credentials.json"


    fun build(): GoogleSheetHelper {
        // Build a new authorized API client service.
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()

        service = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build()

        return this
    }

    private fun getValueToString(case: String): String? {
        service?.let { sheets ->
            val range = "$nameGSClass!$case"
            val response = sheets.spreadsheets().values()[ID, range].execute()
            val result = response.getValues()
            if (result == null || result.isEmpty()) return null
            return result[0][0].toString()
        }
        return null
    }

    private fun getValuesToString(rangeFrom: String, rangTo: String): List<String>? {
        service?.let { sheets ->
            val range = "$nameGSClass!$rangeFrom:$rangTo"
            val response = sheets.spreadsheets().values()[ID, range].execute()
            val result = response.getValues()
            if (result == null || result.isEmpty()) return null
            return result.map { it[0].toString() }
        }
        return null
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {

        // Load client secrets.
        val resource = GoogleSheetHelper::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
                ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(resource))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build()

        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

}