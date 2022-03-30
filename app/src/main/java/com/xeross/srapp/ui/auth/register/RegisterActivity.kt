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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : BaseActivity() {
    
    companion object {
        // ~1s
        private const val BUTTON_REGISTER__DELAY = 1 * 1000L
    }
    
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
        setInputText()
    }
    
    private fun setInputText() {
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
    
    private fun getField(registerTextInputTypes: RegisterTextInputTypes): String? {
        val inputText = textInputs[registerTextInputTypes] ?: return null
        val text = inputText.editText?.text?.takeIf { it.isNotBlank() } ?: run {
            inputText.error(this@RegisterActivity, R.string.field_is_required)
            return null
        }
        return text.toString()
    }
    
    private fun errorEditText(vararg registerTextInputTypes: ExceptionRegisterTypes) {
        registerTextInputTypes.forEach { ex ->
            ex.resId?.let { resId ->
                ex.textInputTypes?.let {
                    textInputs[it]?.let { input ->
                        input.helperText = null
                        input.error(this@RegisterActivity, resId)
                    }
                }
            }
        }
    }
    
    private fun clearTextInputError() {
        for (input in textInputs.values) input.error = null
    }
    
    private fun successRegister() {
        goToActivity<MainActivity>()
    }
    
    private fun register() {
        
        buttonRegisterAntiSpam()
        clearTextInputError()
        
        val pseudo = getField(RegisterTextInputTypes.PSEUDO) ?: return
        val email = getField(RegisterTextInputTypes.EMAIL) ?: return
        val password = getField(RegisterTextInputTypes.PASSWORD) ?: return
        val confirmPassword = getField(RegisterTextInputTypes.CONFIRM_PASSWORD) ?: return
        
        viewModel?.register(pseudo, email, password, confirmPassword)?.observe(this, { ex ->
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successRegister()
                    else -> errorEditText(*it)
                }
            }
        })
    }
    
    private fun buttonRegisterAntiSpam() {
        register_button.isEnabled = false
        
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(BUTTON_REGISTER__DELAY)
            
            register_button.isEnabled = true
        }
    }
    
}