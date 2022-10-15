package com.xeross.srapp.ui.category.category.types

import com.xeross.srapp.R
import com.xeross.srapp.data.models.Category

enum class CategorySortType(val resId: Int, val resStringId: Int, val comparator: Comparator<Category>) {
    LAST_UPDATE(R.id.recently, R.string.recently, Comparator { c1, c2 ->
        return@Comparator (c2.lastUpdatedAt.seconds - c1.lastUpdatedAt.seconds).toInt()
    }),
    NAME_ASC(R.id.name_asc, R.string.name_asc, Comparator { c1, c2 ->
        return@Comparator (c2.name.lowercase()).compareTo(c1.name.lowercase())
    }),
    NAME_DESC(R.id.name_desc, R.string.name_desc, Comparator { c1, c2 ->
        return@Comparator (c1.name.lowercase()).compareTo(c2.name.lowercase())
    }),
}