package com.xeross.srapp.ui.settings

import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.ui.settings.types.SettingType

class SettingViewModel : BaseFirebaseViewModel() {
    
    fun disconnect() {
        disconnectFromFirebase()
    }
    
    fun getToggleSharedPreferences(settingType: SettingType): Boolean {
        return getSharedPreferences(settingType)
    }
    
    fun toggleSharedPreferences(settingType: SettingType, toggle: Boolean) {
        applySharedPreferences(settingType, toggle)
    }
    
}