package com.xeross.srapp.ui.auth.login

import android.content.Intent
import android.util.Log
import androidx.viewbinding.ViewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.xeross.srapp.R
import com.xeross.srapp.databinding.ActivityLoginBinding
import com.xeross.srapp.ui.auth.BaseAuthActivity
import com.xeross.srapp.ui.auth.register.RegisterActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes
import com.xeross.srapp.ui.category.category.CategoryActivity

class LoginActivity : BaseAuthActivity<ActivityLoginBinding>() {
    
    companion object {
        private const val RC_SIGN_IN = 120
        private const val TAG = "LoginActivity"
    }
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass() = LoginViewModel::class.java
    
    private lateinit var googleSignInClient: GoogleSignInClient
    
    private var viewModel: LoginViewModel? = null
    override fun setUp() {
        viewModel = (vm as LoginViewModel)
        viewModel?.build()
        
        setUpGoogle()
    }
    
    override fun ui() {
        setInputText()
    }
    
    private fun setUpGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    
    private fun setInputText() {
        textInputs.apply {
            add(AuthTextInputTypes.EMAIL, binding.emailEditText)
            add(AuthTextInputTypes.PASSWORD, binding.passwordEditText)
        }
    }
    
    
    private fun startIntentGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    
    private fun loginWithGoogle(vm: LoginViewModel, tokenId: String) {
        vm.loginWithGoogle(tokenId).observe(this, { ex ->
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successLogin()
                    else -> errorEditText(*it)
                }
            }
        })
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == RC_SIGN_IN) {
            viewModel?.let { vm ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val exception = task.exception
                
                if (task.isSuccessful) {
                    try {
                        val account = task.getResult(ApiException::class.java)
                        val tokenId = account.idToken ?: return
                        loginWithGoogle(vm, tokenId)
                    } catch (e: ApiException) {
                        Log.w(TAG, "Google sign in failed", e)
                    }
                } else {
                    Log.w(TAG, exception.toString())
                }
            }
        }
    }
    
    override fun onClick() {
        binding.registerTextButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            
            startActivity(intent)
        }
        
        binding.loginButton.setOnClickListener {
            login()
        }
        
        binding.googleButtonCardview.setOnClickListener {
            startIntentGoogle()
        }
        
        // TODO("Update icon password")
    }
    
    private fun successLogin() {
        goToActivity<CategoryActivity>()
    }
    
    private fun getFieldOrNull(authTextInputTypes: AuthTextInputTypes): String? {
        val field = getField(authTextInputTypes)
        if (field != null) return field
        binding.loginButton.antiSpam()
        return null
    }
    
    private fun login() {
        binding.loginButton.isEnabled = false
        clearTextInputError()
        
        val email = getFieldOrNull(AuthTextInputTypes.EMAIL) ?: return
        val password = getFieldOrNull(AuthTextInputTypes.PASSWORD) ?: return
        
        viewModel?.login(email, password)?.observe(this, { ex ->
            binding.loginButton.antiSpam()
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successLogin()
                    else -> errorEditText(*it)
                }
            }
        })
    }
    
    
}