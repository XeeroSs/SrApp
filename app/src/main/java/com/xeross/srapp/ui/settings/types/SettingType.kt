package com.xeross.srapp.ui.settings.types

import com.xeross.srapp.R
import com.xeross.srapp.listener.ISharedPreferences

enum class SettingType(private val category: SettingCategoryTypes, private val resLabelId: Int,
                       private val resDescriptionId: Int, private val keySharedPreferences: String, private val defaultValue: Boolean) : ISharedPreferences<Boolean> {
    
    DISPLAY_MILLISECONDS(SettingCategoryTypes.SUBCATEGORY, R.string.display_milliseconds, R.string.display_milliseconds_on_time_average_from_subcategory, "DISPLAY_MILLISECONDS", true),
    DISPLAY_IMAGE_IN_SUBCATEGORY(SettingCategoryTypes.SUBCATEGORY, R.string.display_milliseconds, R.string.display_milliseconds_on_time_average_from_subcategory, "DISPLAY_IMAGE_IN_SUBCATEGORY", false),
    
    ;
    
    override fun getKey() = keySharedPreferences
    override fun getDefaultValue() = defaultValue
}