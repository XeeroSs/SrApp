package com.xeross.srapp.data.models.types

import com.xeross.srapp.R

enum class StatisticType(val resStringId: Int, val defaultValue: String) {
    
    BEST(R.string.game_details_stats_sum_of_best, "0:00:00.000"),
    AVERAGE(R.string.game_details_stats_average, "0:00:00.000"),
    WORST(R.string.game_details_stats_sum_of_worst, "0:00:00.000"),
    TOTAL(R.string.game_details_stats_runs_amount, "0"),
    
}