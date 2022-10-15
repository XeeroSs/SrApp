package com.xeross.srapp.ui.category.subcategories

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseActivity
import com.xeross.srapp.components.ui.GraphicBar
import com.xeross.srapp.components.ui.models.BarData
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.databinding.ActivitySubcategoriesBinding
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.SubcategoriesAdapter
import com.xeross.srapp.ui.category.management.category.CategoryManagementActivity
import com.xeross.srapp.ui.category.management.subcategory.SubcategoryManagementActivity
import com.xeross.srapp.ui.category.subcategory.SubcategoryActivity
import com.xeross.srapp.ui.categoryform.subcategory.SubcategoryFormActivity
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import com.xeross.srapp.utils.livedata.ResultLiveDataType

class SubcategoriesActivity : BaseActivity<ActivitySubcategoriesBinding>(), ClickListener<SubCategory>, SubcategoriesAdapter.GraphicListener {
    
    companion object {
        const val RC_REFRESH = 888
    }
    
    override fun attachViewBinding(): ViewBinding {
        return ActivitySubcategoriesBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass() = SubcategoriesViewModel::class.java
    private var viewModel: SubcategoriesViewModel? = null
    
    private var subCategories = ArrayList<SubCategory>()
    private var adapter: SubcategoriesAdapter? = null
    
    private lateinit var categoryId: String
    private lateinit var categoryName: String
    
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RC_REFRESH) {
            setResult(SubcategoriesActivity.RC_REFRESH)
            refresh()
        }
    }
    
    override fun setUp() {
        viewModel = vm as SubcategoriesViewModel?
        viewModel?.build()
        
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: "???"
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: run {
            // TODO("do error message")
            finish()
            return
        }
        
    }
    
    // TODO("make color gradient")
    override fun ui() {
        
        buildHeader(binding.header.headerToolbar, binding.header.headerTitle, R.string.subcategory, 25f)
        
        buildBottomNavigationMenu(binding.menu.bottomNavigationMenu)
        setStatusBarTransparent()
        
        setSupportActionBar(binding.header.headerToolbar)
        supportActionBar?.title = null
        
        binding.header.headerToolbar.setNavigationOnClickListener {
            finish()
        }
        
        adapter = SubcategoriesAdapter(this, subCategories, this, this).also { a ->
            binding.listSubcategories.apply {
                val linearLayoutManager = LinearLayoutManager(this@SubcategoriesActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                itemAnimator = DefaultItemAnimator()
                adapter = a
            }
        }
        
        binding.listSubcategories.post {
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = binding.listSubcategories.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = binding.menu.bottomNavigationMenu.measuredHeight
        }
        
        refresh()
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.subcategory_menu_settings -> {
            
            val intent = Intent(this, CategoryManagementActivity::class.java)
            
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            
            resultLauncher.launch(intent)
            
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.subcategory_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    private fun refresh() {
        subCategories.clear()
        adapter?.notifyDataSetChanged()
        viewModel?.getSubcategories(categoryId)?.observe(this) {
            it.takeIf { it != null && it.isNotEmpty() }?.let { list ->
                refresh(list)
            }
        }
    }
    
    private fun refresh(list: ArrayList<SubCategory>) {
        subCategories.clear()
        subCategories.addAll(list)
        adapter?.notifyDataSetChanged()
    }
    
    override fun onClick() {
        binding.addSubcategory.setOnClickListener {
            val intent = Intent(this, SubcategoryFormActivity::class.java)
            //     intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            
            resultLauncher.launch(intent)
        }
    }
    
    override fun notifyGraphicDataChanged(graphicBar: GraphicBar, subCategory: SubCategory) {
        
        val limit = graphicBar.getBarTotal()
        
        viewModel?.getTimeWithLimit(categoryId, subCategory.id, limit)?.observe(this) {
            if (it != null && it.state != ResultLiveDataType.SUCCESS) return@observe
            
            val bars = ArrayList<BarData>()
            
            it.result!!.forEach { time ->
                bars.add(BarData(time.time))
            }
            
            graphicBar.setBars(bars)
        }
    }
    
    override fun onClick(o: SubCategory) {
        val intent = Intent(this, SubcategoryActivity::class.java)
        
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
        
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_ID, o.id)
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_NAME, o.name)
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_URL, o.imageURL)
        
        resultLauncher.launch(intent)
    }
    
    override fun onLongClick(o: SubCategory) {
    }
}