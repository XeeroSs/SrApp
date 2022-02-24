package com.xeross.srapp.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xeross.srapp.R
import com.xeross.srapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin


class SplashScreenActivity : AppCompatActivity() {
    
    companion object {
        // ~2s
        private const val SPLASH_DELAY = 2 * 1000L
        private const val TAG = "SplashScreenActivity"
    }
    
    private var endActivity = false
    
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
        
        val width = app_name.paint.measureText(app_name.text.toString())
        // Get gradient angle
        val angle = 45f
        val widthAngle = (sin(Math.PI * angle / 180) * width).toFloat()
        val heightAngle = (cos(Math.PI * angle / 180) * width).toFloat()
        
        val textShader: Shader = LinearGradient(0f, 0f, widthAngle, heightAngle, colors, null, TileMode.CLAMP)
        
        // Remove the "white cover" on text
        app_name.setTextColor(Color.WHITE)
        
        // Set gradient on text
        app_name.paint.shader = textShader
    }
    
    private fun sleep() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        
        // Transition animation
        val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair(app_name, "header"))
        
        // start transition animation
        startActivity(intent, options.toBundle())
        
        endActivity = true
    }
    
    
}