package com.xeross.srapp.ui.auth

import android.content.Intent
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes
import com.xeross.srapp.utils.extensions.UIExtensions.error
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseAuthActivity : BaseActivity() {
    
    protected val textInputs = HashMap<AuthTextInputTypes, TextInputLayout>()
    
    companion object {
        // ~1s
        private const val BUTTON_REGISTER_DELAY = 1 * 1000L
        
        const val PSEUDO_EXTRA_REGISTER = "pseudo_register"
        const val EMAIL_EXTRA_REGISTER = "email_register"
        const val PASSWORD_EXTRA_REGISTER = "password_register"
        const val CONFIRM_PASSWORD_EXTRA_REGISTER = "confirm_password_register"
    }
    
    protected fun MaterialButton.antiSpam() {
        this.isEnabled = false
        
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(BUTTON_REGISTER_DELAY)
            
            this@antiSpam.isEnabled = true
        }
    }
    
    protected fun HashMap<AuthTextInputTypes, TextInputLayout>.add(authTextInputTypes: AuthTextInputTypes, textInputLayout: TextInputLayout) {
        put(authTextInputTypes, textInputLayout)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()
    }
    
    protected fun getField(authTextInputTypes: AuthTextInputTypes): String? {
        val inputText = textInputs[authTextInputTypes] ?: return null
        val text = inputText.editText?.text?.takeIf { it.isNotBlank() } ?: run {
            inputText.error(this, R.string.field_is_required)
            return null
        }
        return text.toString()
    }
    
    protected fun Intent.sendExtra(textInputTypes: TextInputLayout, extraKey: String) {
        textInputTypes.editText?.text.toString().takeIf { it.isNotEmpty() || it.isNotBlank() }?.let {
            this.putExtra(extraKey, it)
        }
    }
    
    protected fun errorEditText(vararg registerTextInputTypes: ExceptionRegisterTypes) {
        registerTextInputTypes.forEach { ex ->
            ex.resId?.let { resId ->
                ex.textInputTypes?.let {
                    textInputs[it]?.let { input ->
                        input.helperText = null
                        input.error(this, resId)
                    }
                }
            }
        }
    }
    
    protected fun clearTextInputError() {
        for (input in textInputs.values) input.error = null
    }
    
}