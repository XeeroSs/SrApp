package com.xeross.srapp.ui.settings

import com.google.android.material.switchmaterial.SwitchMaterial
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity.Companion.RC_REFRESH
import com.xeross.srapp.ui.settings.types.SettingType
import com.xeross.srapp.utils.Constants
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    
    override fun getFragmentId() = R.layout.activity_setting
    
    override fun getViewModelClass() = SettingViewModel::class.java
    private var viewModel: SettingViewModel? = null
    
    private val switches = HashMap<SettingType, SwitchMaterial>()
    
    override fun setUp() {
        viewModel = (vm as SettingViewModel?)?.also {
            it.build()
            it.buildSharedPreferences(this, Constants.KEY_SHARED_PREFERENCES)
        }
    }
    
    override fun ui() {
        buildHeader(R.string.settings, 35f)
        setStatusBarTransparent()
        
        switches[SettingType.DISPLAY_MILLISECONDS] = switch_display_milliseconds
        
        initSwitch()
    }
    
    private fun initSwitch() {
        viewModel?.let { vm ->
            switches.forEach { mapSwitch ->
                val settingType = mapSwitch.key
                val toggle = vm.getToggleSharedPreferences(settingType)
                val switch = mapSwitch.value
                switch.isChecked = toggle
                
                switch.setOnCheckedChangeListener { _, isChecked ->
                    vm.toggleSharedPreferences(settingType, isChecked)
                    setResult(RC_REFRESH)
                }
            }
        }
    }
    
    override fun onClick() {
        
        item_disconnection.setOnClickListener { _ ->
            viewModel?.let {
                it.disconnect()
                goToActivity<LoginActivity>()
            }
        }
        
    }
}