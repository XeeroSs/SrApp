package com.xeross.srapp.network.google

import androidx.lifecycle.LiveData

interface CelesteSheetAPI {

    fun fetchRuns(nameGSClass: String): LiveData<List<String>?>
    fun fetchRunsAverage(nameGSClass: String): LiveData<String?>
    fun fetchBestRun(nameGSClass: String): LiveData<String?>
    fun fetchWorstRun(nameGSClass: String): LiveData<String?>
    fun fetchRunAmount(nameGSClass: String): LiveData<String?>

    /**
     * Fetch the world record runs .
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?>

    /**
     * Fetch the world record runs into any% run world record.
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchAnyPercentRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?>
    fun fetchSRNameAnyPercentRunsWordRecord(nameGSClass: String): LiveData<Map<String, String>?>

    /**
     * Fetch the differnce time betwwen the any % world record and your personnel best
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchDifferenceBetweenPBAndWR(nameGSClass: String): LiveData<Map<String, String>?>

    fun fetchFRRunAnyPercent(nameGSClass: String): LiveData<Map<String, String>?>
    fun fetchSRNameFRRunAnyPercent(nameGSClass: String): LiveData<Map<String, String>?>

    fun fetchDifferenceBetweenPBAndFRWR(nameGSClass: String): LiveData<Map<String, String>?>

}