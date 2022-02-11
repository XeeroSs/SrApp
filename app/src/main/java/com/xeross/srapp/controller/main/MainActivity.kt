package com.xeross.srapp.controller.main

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.adapter.GameAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.controller.celeste.CelesteActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import com.xeross.srapp.model.SpeedrunType
import kotlinx.android.synthetic.main.activity_celeste.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_navigation_menu


class MainActivity : BaseActivity(), ClickListener<Game> {
    
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main
    
    private lateinit var adapter: GameAdapter
    private val categories = ArrayList<Game>()
    
    private var viewModel: MainViewModel? = null
    
    override fun build() {
        viewModel = (vm as MainViewModel)
        viewModel?.build()
        adapter = GameAdapter(this, categories, this).also { a ->
            main_activity_list_categories.let {
                val linearLayoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
        handleUI()
        
        test()
    }
    
    private fun handleUI() {
        
        main_activity_list_categories.post {
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = main_activity_list_categories.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = bottom_navigation_menu.measuredHeight
        }
        
        
        // TODO("Set in BaseActivity")
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        
        handleBottomNavigationMenu()
    }
    
    // TODO("Set in BaseActivity")
    private fun handleBottomNavigationMenu() {
        // unselected the first item (the first item is selected by default when the activity is created)
        bottom_navigation_menu.menu.getItem(0).isCheckable = false
        (bottom_navigation_menu as NavigationBarView).setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            // Test
            when (it.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, CelesteActivity::class.java)
                    startActivity(intent)
                }
            }
            // return false allows don't show color after selected item
            return@OnItemSelectedListener false
        })
    }
    
    private fun test() {
        categories.add(Game(SpeedrunType.CELESTE, "Celeste IL", R.drawable.im_celeste, 2))
        categories.add(Game(SpeedrunType.CELESTE, "Banjo Kazooie", R.drawable.im_celeste_level_1, 75))
        categories.add(Game(SpeedrunType.CELESTE, "Super Mario 75", R.drawable.im_celeste_level_2, 3))
        categories.add(Game(SpeedrunType.CELESTE, "Ocarina Of Time", R.drawable.im_celeste_level_3, 222))
        categories.add(Game(SpeedrunType.CELESTE, "SMB3", R.drawable.im_celeste_level_4, 1))
        categories.add(Game(SpeedrunType.CELESTE, "Majora's Maks", R.drawable.im_celeste_level_5, 3335))
        categories.add(Game(SpeedrunType.CELESTE, "Celeste", R.drawable.im_celeste_level_6, 52))
        adapter.notifyDataSetChanged()
    }
    
    override fun onClick(o: Game) {
        when (o.idSRC) {
            SpeedrunType.CELESTE -> {
                val intent = Intent(this, CelesteActivity::class.java)
                startActivity(intent)
                return
            }
        }
    }
    
    override fun onLongClick(o: Game) {}
}