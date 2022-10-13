package com.xeross.srapp.ui.category.category

import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseActivity
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.databinding.ActivityCategoryBinding
import com.xeross.srapp.listener.AsyncRecyclerListener
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.CategoryAdapter
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import com.xeross.srapp.utils.livedata.ResultLiveDataType


class CategoryActivity : BaseActivity<ActivityCategoryBinding>(), ClickListener<Category>, AsyncRecyclerListener<CategoryAdapter.ViewHolder, Category> {
    
    companion object {
        const val RC_CREATE_NEW_CATEGORY = 25
    }
    
    override fun getViewModelClass() = CategoryViewModel::class.java
    
    private lateinit var glide: RequestManager
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityCategoryBinding.inflate(layoutInflater)
    }
    
    private lateinit var adapter: CategoryAdapter
    private val categories = ArrayList<Category>()
    
    private var viewModel: CategoryViewModel? = null
    
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == SubcategoriesActivity.RC_REFRESH) {
            getCategories()
        }
    }
    
    override fun setUp() {
        viewModel = (vm as CategoryViewModel)
        viewModel?.buildViewModel()
        
        glide = Glide.with(this)
        
        getCategories()
    }
    
    override fun ui() {
        
        buildHeader(null, binding.headerTitle, R.string.category, 35f)
        
        adapter = CategoryAdapter(this, categories, this, this).also { a ->
            binding.mainActivityListCategories.let {
                val linearLayoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
        binding.mainActivityListCategories.post {
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = binding.mainActivityListCategories.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = binding.menu.bottomNavigationMenu.measuredHeight
        }
        
        
        setStatusBarTransparent()
        buildBottomNavigationMenu(binding.menu.bottomNavigationMenu)
    }
    
    override fun onClick() {
        binding.mainActivityButtonAddYourCategories.setOnClickListener {
            val intent = Intent(this, CategoryFormActivity::class.java)
            resultLauncher.launch(intent)
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
        viewModel?.getCategories()?.observe(this) {
            if (it == null) return@observe
            categories.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
    
    override fun execute(holder: CategoryAdapter.ViewHolder, dObject: Category) {
        viewModel?.getImageFromStorage(dObject.id)?.observe(this) { query ->
            if (query.state == ResultLiveDataType.FAIL) {
                holder.image.scaleType = ImageView.ScaleType.CENTER_INSIDE
                glide.load(R.drawable.ill_gaming_amico).into(holder.image)
                return@observe
            }
            
            query.result?.let { uri ->
                glide.load(uri.toString()).into(holder.image)
            }
        }
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