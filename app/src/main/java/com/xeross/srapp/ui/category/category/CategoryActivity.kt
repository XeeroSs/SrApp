package com.xeross.srapp.ui.category.category

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.CategoryAdapter
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation_menu.*


class CategoryActivity : BaseActivity(), ClickListener<Category> {
    
    companion object {
        const val RC_CREATE_NEW_CATEGORY = 25
    }
    
    override fun getViewModelClass() = CategoryViewModel::class.java
    override fun getFragmentId() = R.layout.activity_category
    
    private lateinit var adapter: CategoryAdapter
    private val categories = ArrayList<Category>()
    
    private var viewModel: CategoryViewModel? = null
    
    override fun setUp() {
        viewModel = (vm as CategoryViewModel)
        viewModel?.buildViewModel()
        
        getCategories()
    }
    
    override fun ui() {
        
        buildHeader(R.string.category, 35f)
        
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
        
        
        setStatusBarTransparent()
        buildBottomNavigationMenu()
    }
    
    override fun onClick() {
        main_activity_button_add_your_categories.setOnClickListener {
            goToActivity<CategoryFormActivity>(false)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RC_CREATE_NEW_CATEGORY) {
            getCategories()
        }
    }
    
    private fun getCategories() {
        categories.clear()
        viewModel?.getCategories()?.observe(this, {
            if (it == null) return@observe
            categories.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
    
    override fun onClick(o: Category) {
        val intent = Intent(this, SubcategoriesActivity::class.java)
        //     intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
        intent.putExtra(EXTRA_CATEGORY_ID, o.id)
        intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
        startActivity(intent)
        return
    }
    
    override fun onLongClick(o: Category) {}
}