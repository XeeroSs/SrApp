package com.xeross.srapp.utils.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.ui.auth.login.LoginViewModel
import com.xeross.srapp.ui.auth.register.RegisterViewModel
import com.xeross.srapp.ui.category.category.CategoryViewModel
import com.xeross.srapp.ui.category.subcategories.SubcategoriesViewModel
import com.xeross.srapp.ui.category.subcategory.SubcategoryViewModel
import com.xeross.srapp.ui.categoryform.category.CategoryFormViewModel
import com.xeross.srapp.ui.categoryform.subcategory.SubcategoryFormViewModel
import com.xeross.srapp.ui.profile.ProfileViewModel
import com.xeross.srapp.ui.settings.SettingViewModel
import com.xeross.srapp.ui.splash.SplashViewModel
import com.xeross.srapp.ui.times.TimesViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return when {
            getViewModel<SplashViewModel>(modelClass) -> SplashViewModel()
            getViewModel<SubcategoryViewModel>(modelClass) -> SubcategoryViewModel()
            getViewModel<SubcategoryFormViewModel>(modelClass) -> SubcategoryFormViewModel()
            getViewModel<CategoryFormViewModel>(modelClass) -> CategoryFormViewModel()
            getViewModel<SubcategoriesViewModel>(modelClass) -> SubcategoriesViewModel()
            getViewModel<TimesViewModel>(modelClass) -> TimesViewModel()
            getViewModel<CategoryViewModel>(modelClass) -> CategoryViewModel()
            getViewModel<RegisterViewModel>(modelClass) -> RegisterViewModel()
            getViewModel<ProfileViewModel>(modelClass) -> ProfileViewModel()
            getViewModel<LoginViewModel>(modelClass) -> LoginViewModel()
            getViewModel<SettingViewModel>(modelClass) -> SettingViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as VM
    }
    
    private inline fun <reified VM> getViewModel(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(VM::class.java)
    }
}