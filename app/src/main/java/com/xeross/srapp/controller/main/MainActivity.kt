package com.xeross.srapp.controller.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.xeross.srapp.model.types.SpeedrunType
import kotlinx.android.synthetic.main.activity_celeste.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_navigation_menu


class MainActivity : BaseActivity(), ClickListener<Game> {
    
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main
    
    private lateinit var adapter: GameAdapter
    private val categories = ArrayList<Game>()
    
    private var viewModel: MainViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
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
        
        getCategories()
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
    
    private fun getCategories() {
        
        viewModel?.getCeleste(this)?.observe(this, {
    
            it?.let { game ->
                categories.add(game)
                adapter.notifyDataSetChanged()
            }
    
        })
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