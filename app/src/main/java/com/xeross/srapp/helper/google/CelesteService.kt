package com.xeross.srapp.helper.google

import androidx.lifecycle.LiveData

interface CelesteService {

    fun fetchRuns(): LiveData<List<String>?>
    fun fetchRunsAverage(): LiveData<String?>
    fun fetchBestRun(): LiveData<String?>
    fun fetchWorstRun(): LiveData<String?>
    fun fetchRunAmount(): LiveData<String?>

    /**
     * Fetch the world record runs .
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchRunsWordRecord(): LiveData<Map<String, String>?>

    /**
     * Fetch the world record runs into any% run world record.
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchAnyPercentRunsWordRecord(): LiveData<Map<String, String>?>
    fun fetchSRNameAnyPercentRunsWordRecord(): LiveData<Map<String, String>?>

    /**
     * Fetch the differnce time betwwen the any % world record and your personnel best
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchDifferenceBetweenPBAndWR(): LiveData<Map<String, String>?>

    fun fetchFRRunAnyPercent(): LiveData<Map<String, String>?>
    fun fetchSRNameFRRunAnyPercent(): LiveData<Map<String, String>?>

    fun fetchDifferenceBetweenPBAndFRWR(): LiveData<Map<String, String>?>

}