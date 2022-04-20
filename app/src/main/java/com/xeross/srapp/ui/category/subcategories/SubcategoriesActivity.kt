package com.xeross.srapp.ui.category.subcategories

import android.content.Intent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.SubcategoriesAdapter
import com.xeross.srapp.ui.category.subcategory.SubCategoryActivity
import com.xeross.srapp.ui.categoryform.subcategory.SubcategoryFormActivity
import com.xeross.srapp.ui.celeste.CelesteActivity
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import kotlinx.android.synthetic.main.activity_subcategories.*
import kotlinx.android.synthetic.main.activity_subcategories.bottom_navigation_menu
import kotlinx.android.synthetic.main.activity_subcategory.*
import kotlinx.android.synthetic.main.cell_subcategory.*

class SubcategoriesActivity : BaseActivity(), ClickListener<SubCategory> {
    
    companion object {
        const val RC_CREATE_NEW_SUBCATEGORY = 888
    }
    
    override fun getFragmentId() = R.layout.activity_subcategories
    
    override fun getViewModelClass() = SubcategoriesViewModel::class.java
    private var viewModel: SubcategoriesViewModel? = null
    
    private var subCategories = ArrayList<SubCategory>()
    private var adapter: SubcategoriesAdapter? = null
    
    private lateinit var categoryId: String
    
    override fun setUp() {
        viewModel = vm as SubcategoriesViewModel?
        viewModel?.build()
        
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: run {
            // TODO("do error message")
            finish()
            return
        }
        
    }
    
    // TODO("make color gradient")
    override fun ui() {
        
        handleBottomNavigationMenu()
        setStatusBarTransparent()
        
        adapter = SubcategoriesAdapter(this, subCategories, this).also { a ->
            list_subcategories.apply {
                val linearLayoutManager = LinearLayoutManager(this@SubcategoriesActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                itemAnimator = DefaultItemAnimator()
                adapter = a
            }
        }
        
        viewModel?.getSubcategories(categoryId)?.observe(this, {
            it.takeIf { it != null && it.isNotEmpty() }?.let { list ->
                refresh(list)
            }
        })
    }
    
    private fun refresh(list: ArrayList<SubCategory>) {
        subCategories.clear()
        subCategories.addAll(list)
        adapter?.notifyDataSetChanged()
    }
    
    override fun onClick() {
        add_subcategory.setOnClickListener {
            val intent = Intent(this, SubcategoryFormActivity::class.java)
            //     intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            startActivity(intent)
        }
    }
    
    override fun onClick(o: SubCategory) {
        val intent = Intent(this, SubCategoryActivity::class.java)
        
        intent.putExtra(EXTRA_CATEGORY_ID, o.id)
        intent.putExtra(Constants.EXTRA_CATEGORY_NAME, o.name)
        
        startActivity(intent)
    }
    
    // TODO("Put into base activity")
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
    
    override fun onLongClick(o: SubCategory) {
    }
}