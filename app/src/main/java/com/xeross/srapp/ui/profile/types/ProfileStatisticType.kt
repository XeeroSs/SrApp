package com.xeross.srapp.ui.profile.types

import com.xeross.srapp.R

enum class ProfileStatisticType(val resNameId: Int, val resColorId: Int, val resIconId: Int) {
    
    TOTAL_TIMES(R.string.total_times, R.color.positive, R.drawable.ic_flag),
    PB_RATE(R.string.best_rate, R.color.blue_pastel, R.drawable.ic_profits_2),
    TOTAL_PB(R.string.total_bests, R.color.gold, R.drawable.ic_mission),
    LAST_PB_AT(R.string.last_best, R.color.purple_pastel, R.drawable.ic_starred),
    ACCOUNT_CREATED_AT(R.string.account_created_at, R.color.negative, R.drawable.ic_agenda),
    
}