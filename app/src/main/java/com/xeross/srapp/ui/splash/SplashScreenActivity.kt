package com.xeross.srapp.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.main.MainActivity
import com.xeross.srapp.utils.extensions.UIExtensions.setTintGradient
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : BaseActivity() {
    
    override fun getFragmentId() = R.layout.activity_splash_screen
    override fun getViewModelClass() = SplashViewModel::class.java
    
    companion object {
        // ~2s
        private const val SPLASH_DELAY = 2 * 1000L
        private const val TAG = "SplashScreenActivity"
    }
    
    private var endActivity = false
    
    private var viewModel: SplashViewModel? = null
    
    override fun build() {
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
        
        
        logo.setTintGradient(colors, applicationContext)
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
        
        
        logo.setTintGradient(colors, applicationContext)
    }
    
    private fun sleep() {
        
        val intentIfAuth = if (viewModel?.isAuth() == true) MainActivity::class.java else LoginActivity::class.java
        
        val intent = Intent(applicationContext, intentIfAuth)
        
        // Transition animation
        val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair(logo, "header"))
        
        // start transition animation
        startActivity(intent, options.toBundle())
        
        endActivity = true
    }
    
    
}