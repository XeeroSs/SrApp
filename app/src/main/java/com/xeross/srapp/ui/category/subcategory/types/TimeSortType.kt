package com.xeross.srapp.ui.category.subcategory.types

import com.xeross.srapp.R

enum class TimeSortType(val resId: Int, val resStringId: Int, val timeToDays: Int) {
    WEEK(R.id.week, R.string.week, 7),
    MONTH(R.id.month, R.string.month, 30),
    YEAR(R.id.years, R.string.years, 365),
    ALL_RUNS(R.id.all_runs, R.string.all_runs, -1),
}