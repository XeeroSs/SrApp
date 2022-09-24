package com.xeross.srapp.ui.category.management

import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.databinding.ActivityCategoryManagementBinding
import com.xeross.srapp.ui.settings.manager.SettingViewManager
import com.xeross.srapp.utils.Constants.SUBCATEGORY_BEST_TIME_SHARED_PREFERENCES
import com.xeross.srapp.utils.extensions.UIExtensions
import java.util.function.Function


class CategoryManagementActivity : BaseActivity<ActivityCategoryManagementBinding>() {
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityCategoryManagementBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass() = CategoryManagementViewModel::class.java
    private lateinit var viewModel: CategoryManagementViewModel
    
    private var switchBestTime: SwitchMaterial? = null
    
    override fun setUp() {
        viewModel = vm as CategoryManagementViewModel
        viewModel.build()
    }
    
    override fun ui() {
        
        setStatusBarTransparent()
        buildHeader(binding.headerToolbar, binding.headerTitle, R.string.management, 25f)
        
        with(SettingViewManager(this, binding.categoryManagementContent)) {
            
            category(R.string.global)
            
            val contentColor = LinearLayout(this@CategoryManagementActivity)
            
            contentColor.background = UIExtensions.getGradientWithSingleColor(this@CategoryManagementActivity.applicationContext, R.color.pink_gradient)
            
            subcategoryWithCustomView(R.string.color, contentColor, 50, 50, true, Function { _ ->
                return@Function true
            })
            
            SUBCATEGORY_BEST_TIME_SHARED_PREFERENCES
            switchBestTime = subcategoryWithSwitch(R.string.best_time, R.string.best_is_handle_by_lowest_time, defaultChecked = true, textIsClickable = true, function = Function { _ ->
                return@Function true
            })
            
            category(R.string.other)
            
            subcategoryWithIcon(R.string.delete, R.drawable.ic_trash, true, Function { _ ->
                return@Function true
            })
            
            build()
        }
        
        /*
       double resultRed = color1.red + percent * (color2.red - color1.red);
     double resultGreen = color1.green + percent * (color2.green - color1.green);
     double resultBlue = color1.blue + percent * (color2.blue - color1.blue);
       
       182 255 112
       
       R  182 255 112
        G   182 255 112
          B   182 255 112
       
        *
        * */
        
    }
    
    override fun onClick() {
    }
}