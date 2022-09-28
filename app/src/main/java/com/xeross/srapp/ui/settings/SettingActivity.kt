package com.xeross.srapp.ui.settings

import androidx.viewbinding.ViewBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseActivity
import com.xeross.srapp.databinding.ActivitySettingBinding
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity.Companion.RC_REFRESH
import com.xeross.srapp.ui.settings.types.SettingType
import com.xeross.srapp.utils.Constants

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    
    override fun getViewModelClass() = SettingViewModel::class.java
    private var viewModel: SettingViewModel? = null
    
    private val switches = HashMap<SettingType, SwitchMaterial>()
    
    override fun setUp() {
        viewModel = (vm as SettingViewModel?)?.also {
            it.build()
            it.buildSharedPreferences(this, Constants.KEY_SHARED_PREFERENCES)
        }
    }
    
    override fun attachViewBinding(): ViewBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }
    
    override fun ui() {
        buildHeader(binding.header.headerToolbar, binding.header.headerTitle, R.string.settings, 35f)
        setStatusBarTransparent()
        
        switches[SettingType.DISPLAY_MILLISECONDS] = binding.switchDisplayMilliseconds
        
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
        
        binding.itemDisconnection.setOnClickListener { _ ->
            viewModel?.let {
                it.disconnect()
                goToActivity<LoginActivity>()
            }
        }
        
    }
}