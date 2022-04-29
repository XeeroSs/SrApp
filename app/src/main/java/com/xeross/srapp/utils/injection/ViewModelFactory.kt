package com.xeross.srapp.utils.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.ui.auth.login.LoginViewModel
import com.xeross.srapp.ui.auth.register.RegisterViewModel
import com.xeross.srapp.ui.category.category.CategoryViewModel
import com.xeross.srapp.ui.category.subcategories.SubcategoriesViewModel
import com.xeross.srapp.ui.category.subcategory.SubcategoryViewModel
import com.xeross.srapp.ui.categoryform.category.CategoryFormViewModel
import com.xeross.srapp.ui.categoryform.subcategory.SubcategoryFormViewModel
import com.xeross.srapp.ui.celeste.CelesteViewModel
import com.xeross.srapp.ui.splash.SplashViewModel
import com.xeross.srapp.ui.times.TimesViewModel
import java.lang.ref.WeakReference

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    
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
            getViewModel<LoginViewModel>(modelClass) -> LoginViewModel()
            getViewModel<CelesteViewModel>(modelClass) -> CelesteViewModel(WeakReference(context))
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as VM
    }
    
    private inline fun <reified VM> getViewModel(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(VM::class.java)
    }
}