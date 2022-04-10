package com.xeross.srapp.ui.main

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.ui.adapters.CategoryAdapter
import com.xeross.srapp.ui.adapters.listener.ClickListener
import com.xeross.srapp.ui.auth.login.LoginActivity
import com.xeross.srapp.ui.categoryform.CategoryFormActivity
import com.xeross.srapp.ui.celeste.CelesteActivity
import com.xeross.srapp.ui.details.GameDetailActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), ClickListener<Category> {
    
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main
    
    private lateinit var adapter: CategoryAdapter
    private val categories = ArrayList<Category>()
    
    private var viewModel: MainViewModel? = null
    
    override fun build() {
        viewModel = (vm as MainViewModel)
        viewModel?.buildViewModel()
        
        getCategories()
    }
    
    override fun ui() {
        adapter = CategoryAdapter(this, categories, this).also { a ->
            main_activity_list_categories.let {
                val linearLayoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
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
    
    override fun onClick() {
        main_activity_button_add_your_categories.setOnClickListener {
            goToActivity<CategoryFormActivity>(false)
        }
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
                R.id.menu_settings -> {
                    // TODO("Test")
                    viewModel?.disconnect()
                    goToActivity<LoginActivity>()
                }
            }
            // return false allows don't show color after selected item
            return@OnItemSelectedListener false
        })
    }
    
    private fun getCategories() {
        viewModel?.getCategories()?.observe(this, {
            if (it == null) return@observe
            categories.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
    
    override fun onClick(o: Category) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
        startActivity(intent)
        return
    }
    
    override fun onLongClick(o: Category) {}
}