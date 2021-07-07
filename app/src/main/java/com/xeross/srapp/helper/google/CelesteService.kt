package com.xeross.srapp.helper.google

import java.io.IOException

interface CelesteService {

    fun fetchRuns(): Array<String>?

    fun fetchRunsAverage(): String?

    fun fetchBestRun(): String?

    fun fetchWorstRun(): String?

    /**
     * Fetch the world record runs .
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchRunsWordRecord(): Map<Int, String>?

    /**
     * Fetch the world record runs into any% run world record.
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchAnyPercentRunsWordRecord(): Map<Int, String>?

    /**
     * Fetch the differnce time betwwen the any % world record and your personnel best
     * @return [Int] is leaders board positions & [String] is Time.
     */
    fun fetchDifferenceBetweenPBAndWR(): Map<Int, String>?

    fun fetchFRRunAnyPercent(): Map<Int, String>?

    fun fetchDifferenceBetweenPBAndFRWR(): Map<Int, String>?

}