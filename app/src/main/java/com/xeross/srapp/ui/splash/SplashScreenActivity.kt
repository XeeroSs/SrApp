package com.xeross.srapp.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseActivity
import com.xeross.srapp.databinding.ActivitySplashScreenBinding
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.category.category.CategoryActivity
import com.xeross.srapp.utils.extensions.UIExtensions.setTintGradient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    
    override fun getViewModelClass() = SplashViewModel::class.java
    
    companion object {
        // ~1.2s
        private const val SPLASH_DELAY = 1 * 1200L
        private const val TAG = "SplashScreenActivity"
    }
    
    private var endActivity = false
    
    private var viewModel: SplashViewModel? = null
    
    override fun attachViewBinding(): ViewBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    
    override fun setUp() {
        val view = binding.root
        setContentView(view)
        viewModel = (vm as SplashViewModel)
        viewModel?.build()
    }
    
    override fun onClick() {
    }
    
    override fun ui() {
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DELAY)
            
            // Start main activity et stop this activity
            sleep()
        }
        
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        
        val colors = intArrayOf(
            ContextCompat.getColor(applicationContext, R.color.pink_gradient),
            ContextCompat.getColor(applicationContext, R.color.purple_gradient),
            ContextCompat.getColor(applicationContext, R.color.blue_gradient),
        )
        
        
        binding.logo.setTintGradient(colors, applicationContext)
    }
    
    override fun onStop() {
        super.onStop()
        // Finish the activity after the transition is done
        if (endActivity) finish()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DELAY)
            
            // Start main activity et stop this activity
            sleep()
        }
        
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        
        val colors = intArrayOf(
            ContextCompat.getColor(applicationContext, R.color.pink_gradient),
            ContextCompat.getColor(applicationContext, R.color.purple_gradient),
            ContextCompat.getColor(applicationContext, R.color.blue_gradient),
        )
        
        
        binding.logo.setTintGradient(colors, applicationContext)
    }
    
    private fun sleep() {
        
        val intentIfAuth = if (viewModel?.isAuth() == true) CategoryActivity::class.java else LoginActivity::class.java
        
        val intent = Intent(applicationContext, intentIfAuth)
        
        // Transition animation
        val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair(binding.logo, "header"))
        
        // start transition animation
        startActivity(intent, options.toBundle())
        
        endActivity = true
    }
    
    
}