package com.xeross.srapp.helper.google

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.xeross.srapp.base.BaseActivityOAuth


class CelesteSheetsRepository(viewModel: ViewModel, context: Context, credential: GoogleAccountCredential) : CelesteSheetAPI {
    
    //  private val gsHelper: GoogleSheetHelper = GoogleSheetHelper()
    private var googleSheetDataSource: GoogleSheetDataSource = GoogleSheetDataSource().build(viewModel, context, credential)
    private var baseActivityOAuth: BaseActivityOAuth? = null
    
    fun build(baseActivityOAuth: BaseActivityOAuth): CelesteSheetsRepository {
        //  googleSheetHelper = GoogleSheetHelper(nameSheets).build()
        this.baseActivityOAuth = baseActivityOAuth
        return this
    }
    
    override fun fetchRuns(nameGSClass: String): LiveData<List<String>?> {
        return googleSheetDataSource.getValuesToString(nameGSClass, GoogleSheetConstants.RUNS_FROM, GoogleSheetConstants.RUNS_TO)
    }
    
    override fun fetchRunsAverage(nameGSClass: String): LiveData<String?> {
        return googleSheetDataSource.getValueToString(nameGSClass, GoogleSheetConstants.AVERAGE)
    }
    
    override fun fetchBestRun(nameGSClass: String): LiveData<String?> {
        return googleSheetDataSource.getValueToString(nameGSClass, GoogleSheetConstants.BEST)
    }
    
    override fun fetchWorstRun(nameGSClass: String): LiveData<String?> {
        return googleSheetDataSource.getValueToString(nameGSClass, GoogleSheetConstants.WORST)
    }
    
    override fun fetchRunAmount(nameGSClass: String): LiveData<String?> {
        return googleSheetDataSource.getValueToString(nameGSClass, GoogleSheetConstants.RUNS_AMOUNT)
    }
    
    override fun fetchRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_WORLD_RECORD_TO)
    }
    
    override fun fetchAnyPercentRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchDifferenceBetweenPBAndWR(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_DIFFERENCE_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_DIFFERENCE_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchFRRunAnyPercent(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchDifferenceBetweenPBAndFRWR(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_DIFFERENCE_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_DIFFERENCE_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchSRNameAnyPercentRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_SR_NAME_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_SR_NAME_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchSRNameFRRunAnyPercent(nameGSClass: String): LiveData<Map<String, String>?> {
        return googleSheetDataSource.getValuesToStringMap(nameGSClass, baseActivityOAuth, GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_SR_NAME_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_SR_NAME_ANY_WORLD_RECORD_TO)
    }
}