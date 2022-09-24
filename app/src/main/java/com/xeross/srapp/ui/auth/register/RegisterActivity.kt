package com.xeross.srapp.ui.auth.register

import androidx.viewbinding.ViewBinding
import com.xeross.srapp.databinding.ActivityRegisterBinding
import com.xeross.srapp.ui.auth.BaseAuthActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes
import com.xeross.srapp.ui.category.category.CategoryActivity

class RegisterActivity : BaseAuthActivity<ActivityRegisterBinding>() {
    
    override fun getViewModelClass() = RegisterViewModel::class.java
    
    private var viewModel: RegisterViewModel? = null
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }
    
    override fun setUp() {
        viewModel = (vm as RegisterViewModel)
        viewModel?.build()
    }
    
    private fun setInputText() {
        textInputs.apply {
            add(AuthTextInputTypes.PSEUDO, binding.pseudoEditText)
            add(AuthTextInputTypes.EMAIL, binding.emailEditText)
            add(AuthTextInputTypes.PASSWORD, binding.passwordEditText)
            add(AuthTextInputTypes.CONFIRM_PASSWORD, binding.confirmPasswordEditText)
        }
    }
    
    override fun ui() {
        setInputText()
    }
    
    override fun onClick() {
        binding.loginTextButton.setOnClickListener {
/*            val intent = Intent(this, LoginActivity::class.java).also {
                it.sendExtra(pseudo_edit_text, PSEUDO_EXTRA_REGISTER)
                it.sendExtra(email_edit_text, EMAIL_EXTRA_REGISTER)
                it.sendExtra(password_edit_text, PASSWORD_EXTRA_REGISTER)
                it.sendExtra(confirm_password_edit_text, CONFIRM_PASSWORD_EXTRA_REGISTER)
            }*/
            
            finish()
        }
        
        binding.registerButton.setOnClickListener {
            register()
        }
        
        // TODO("Update icon password")
    }
    
    private fun getFieldOrNull(authTextInputTypes: AuthTextInputTypes): String? {
        val field = getField(authTextInputTypes)
        if (field != null) return field
        binding.registerButton.antiSpam()
        return null
    }
    
    private fun successRegister() {
        goToActivity<CategoryActivity>()
/*        val intent = Intent(applicationContext, MainActivity::class.java)
*//*        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("EXIT", true)
        startActivity(intent)
        finish()*/
    }
    
    private fun register() {
        
        binding.registerButton.isEnabled = false
        clearTextInputError()
        
        val pseudo = getFieldOrNull(AuthTextInputTypes.PSEUDO) ?: return
        val email = getFieldOrNull(AuthTextInputTypes.EMAIL) ?: return
        val password = getFieldOrNull(AuthTextInputTypes.PASSWORD) ?: return
        val confirmPassword = getFieldOrNull(AuthTextInputTypes.CONFIRM_PASSWORD) ?: return
        
        viewModel?.register(pseudo, email, password, confirmPassword)?.observe(this, { ex ->
            binding.registerButton.antiSpam()
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successRegister()
                    else -> errorEditText(*it)
                }
            }
        })
    }
}