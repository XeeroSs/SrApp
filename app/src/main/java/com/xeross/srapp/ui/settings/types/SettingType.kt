package com.xeross.srapp.ui.settings.types

import com.xeross.srapp.listener.ISharedPreferences

enum class SettingType(val keySharedPreferences: String, val defaultValue: Boolean) : ISharedPreferences<Boolean> {
    
    DISPLAY_MILLISECONDS("DISPLAY_MILLISECONDS", true);
    
    override fun getKey() = keySharedPreferences
    override fun getDefaultValue() = defaultValue
}