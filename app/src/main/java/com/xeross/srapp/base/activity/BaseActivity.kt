package com.xeross.srapp.base.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.ui.profile.ProfileActivity
import com.xeross.srapp.ui.settings.SettingActivity
import com.xeross.srapp.utils.injection.ViewModelFactory
import java.util.function.Function

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    
    abstract fun getViewModelClass(): Class<*>
    abstract fun attachViewBinding(): ViewBinding
    abstract fun setUp()
    abstract fun ui()
    abstract fun onClick()
    
    protected var vm: ViewModel? = null
    protected lateinit var binding: B
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = attachViewBinding() as B
        setContentView(binding.root)
        vm = provideViewModel()
        setUp()
        ui()
        onClick()
    }
    
    @Suppress("UNCHECKED_CAST")
    private fun provideViewModel(): ViewModel {
        val factory = ViewModelFactory()
        return ViewModelProvider(this, factory)[getViewModelClass() as Class<ViewModel>]
    }
    
    protected fun setStatusBarTransparent() {
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
    
    protected fun buildHeader(toolbar: MaterialToolbar?, text: TextView, resIdTitle: Int, textSize: Float, function: Function<Void?, Boolean>? = null) {
        
        
        with(text) {
            post {
                this.textSize = textSize
            }
            this.text = resources.getString(resIdTitle)
        }
        toolbar?.setNavigationOnClickListener { _ ->
            function?.let { func ->
                func.apply(null).takeIf { it }?.let { _ ->
                    finish()
                }
                return@setNavigationOnClickListener
            }
            
            finish()
        }
    }
    
    protected fun buildBottomNavigationMenu(view: BottomNavigationView, resultIntent: ActivityResultLauncher<Intent>? = null) {
        // unselected the first item (the first item is selected by default when the activity is created)
        view.menu.getItem(0).isCheckable = false
        (view as NavigationBarView).setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            // Test
            when (it.itemId) {
                R.id.menu_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_home -> {
                }
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    resultIntent?.launch(intent) ?: startActivity(intent)
                }
            }
            // return false allows don't show color after selected item
            return@OnItemSelectedListener false
        })
    }
    
    
    protected fun showMenu(v: View, resMenuId: Int): PopupMenu {
        return PopupMenu(this, v).also {
            it.menuInflater.inflate(resMenuId, it.menu)
            it.show()
        }
    }
    
    protected inline fun <reified T : AppCompatActivity> goToActivity(finishActivity: Boolean = true) {
        val intent = Intent(this, T::class.java)
        if (finishActivity) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        if (finishActivity) finish()
        startActivity(intent)
    }
}