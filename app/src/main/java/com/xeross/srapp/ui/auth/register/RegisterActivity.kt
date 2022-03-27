package com.xeross.srapp.ui.auth.register

import com.google.android.material.textfield.TextInputLayout
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.register.types.RegisterTextInputTypes
import com.xeross.srapp.ui.main.MainActivity
import com.xeross.srapp.utils.extensions.UIExtensions.error
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    
    override fun getFragmentId() = R.layout.activity_register
    
    override fun getViewModelClass() = RegisterViewModel::class.java
    
    private var viewModel: RegisterViewModel? = null
    
    private val textInputs = HashMap<RegisterTextInputTypes, TextInputLayout>()
    
    override fun build() {
        viewModel = (vm as RegisterViewModel)
        viewModel?.build()
        buildUI()
    }
    
    private fun buildUI() {
        setStatusBarTransparent()
        
        onClick()
        
        editText()
    }
    
    private fun editText() {
        textInputs.apply {
            put(RegisterTextInputTypes.PSEUDO, pseudo_edit_text)
            put(RegisterTextInputTypes.EMAIL, email_edit_text)
            put(RegisterTextInputTypes.PASSWORD, password_edit_text)
            put(RegisterTextInputTypes.CONFIRM_PASSWORD, confirm_password_edit_text)
        }
    }
    
    private fun onClick() {
        login_text_button.setOnClickListener {
            goToActivity<LoginActivity>()
        }
        
        register_button.setOnClickListener {
            register()
        }
        
        // TODO("Update icon password")
    }
    
    private fun errorEditText(registerTextInputTypes: Array<ExceptionRegisterTypes>) {
        registerTextInputTypes.forEach { ex ->
            ex.resId?.let { resId ->
                ex.textInputTypes?.let {
                    textInputs[it]?.error(this@RegisterActivity, resId)
                }
            }
        }
    }
    
    private fun successRegister() {
        goToActivity<MainActivity>()
    }
    
    private fun register() {
        viewModel?.register()?.observe(this, { ex ->
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successRegister()
                    else -> errorEditText(it)
                }
            }
        })
    }
    
}