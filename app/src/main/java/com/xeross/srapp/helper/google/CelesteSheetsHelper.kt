package com.xeross.srapp.helper.google

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.xeross.srapp.base.BaseActivityOAuth


class CelesteSheetsHelper(viewModel: ViewModel, nameSheets: String, context: Context, credential: GoogleAccountCredential) : CelesteService {
    
    //  private val gsHelper: GoogleSheetHelper = GoogleSheetHelper()
    private var googleSheetHelper: GoogleSheetHelper = GoogleSheetHelper(nameSheets).build(viewModel, context, credential)
    private var baseActivityOAuth : BaseActivityOAuth?=null
    
    fun build(baseActivityOAuth: BaseActivityOAuth): CelesteSheetsHelper {
        //  googleSheetHelper = GoogleSheetHelper(nameSheets).build()
        this. baseActivityOAuth = baseActivityOAuth
        return this
    }
    
    override fun fetchRuns(): LiveData<List<String>?> {
        return googleSheetHelper.getValuesToString(GoogleSheetConstants.RUNS_FROM, GoogleSheetConstants.RUNS_TO)
    }
    
    override fun fetchRunsAverage(): LiveData<String?> {
        return googleSheetHelper.getValueToString(GoogleSheetConstants.AVERAGE)
    }
    
    override fun fetchBestRun(): LiveData<String?> {
        return googleSheetHelper.getValueToString(GoogleSheetConstants.BEST)
    }
    
    override fun fetchWorstRun(): LiveData<String?> {
        return googleSheetHelper.getValueToString(GoogleSheetConstants.WORST)
    }
    
    override fun fetchRunAmount(): LiveData<String?> {
        return googleSheetHelper.getValueToString(GoogleSheetConstants.RUNS_AMOUNT)
    }
    
    override fun fetchRunsWordRecord(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_WORLD_RECORD_TO)
    }
    
    override fun fetchAnyPercentRunsWordRecord(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchDifferenceBetweenPBAndWR(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_DIFFERENCE_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_DIFFERENCE_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchFRRunAnyPercent(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchDifferenceBetweenPBAndFRWR(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_DIFFERENCE_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_DIFFERENCE_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchSRNameAnyPercentRunsWordRecord(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FROM, GoogleSheetConstants.LB_TO,
            GoogleSheetConstants.RUNS_SR_NAME_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_SR_NAME_ANY_WORLD_RECORD_TO)
    }
    
    override fun fetchSRNameFRRunAnyPercent(): LiveData<Map<String, String>?> {
        return googleSheetHelper.getValuesToStringMap(baseActivityOAuth,GoogleSheetConstants.LB_FR_FROM, GoogleSheetConstants.LB_FR_TO,
            GoogleSheetConstants.RUNS_FR_SR_NAME_ANY_WORLD_RECORD_FROM, GoogleSheetConstants.RUNS_FR_SR_NAME_ANY_WORLD_RECORD_TO)
    }
}