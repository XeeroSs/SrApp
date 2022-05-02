package com.xeross.srapp.ui.categoryform.subcategory

import androidx.fragment.app.Fragment
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity.Companion.RC_REFRESH
import com.xeross.srapp.ui.categoryform.adapters.CategoryFormPageAdapter
import com.xeross.srapp.ui.categoryform.fragment.base.BaseCategoryFormFragment
import com.xeross.srapp.ui.categoryform.interfaces.ICategoryFormType
import com.xeross.srapp.ui.categoryform.types.SubcategoryFormPageType
import com.xeross.srapp.utils.Constants
import kotlinx.android.synthetic.main.activity_subcategory_form.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SubcategoryFormActivity : BaseActivity() {
    
    companion object {
        // ~ 1s
        private const val BUTTON_ANTI_SPAM_DELAY = (1L * 1000)
        
        const val EXTRA_INPUT_TEXT_SUBCATEGORY_FRAGMENT = "EXTRA_INPUT_TEXT_SUBCATEGORY_FRAGMENT"
        const val EXTRA_UPLOAD_IMAGE_SUBCATEGORY_FRAGMENT = "EXTRA_UPLOAD_IMAGE_SUBCATEGORY_FRAGMENT"
    }
    
    override fun getFragmentId() = R.layout.activity_subcategory_form
    
    override fun getViewModelClass() = SubcategoryFormViewModel::class.java
    
    private val extras = HashMap<String, String>()
    
    private var viewModel: SubcategoryFormViewModel? = null
    
    private var isRequest = false
    
    private lateinit var categoryId: String
    
    private var next: String? = null
    private var previous: String? = null
    private var cancel: String? = null
    private var done: String? = null
    
    private val maxPage = SubcategoryFormPageType.values().size
    private var adapter: CategoryFormPageAdapter? = null
    
    override fun setUp() {
        viewModel = vm as SubcategoryFormViewModel?
        viewModel?.build()
        
        categoryId = intent.getStringExtra(Constants.EXTRA_CATEGORY_ID) ?: run {
            // TODO("do error message")
            finish()
            return
        }
    }
    
    
    
    override fun ui() {
        
        buildHeader(R.string.subcategories, 25f) { !isRequest }
    
        setStatusBarTransparent()
        buildViewPager()
        
        next = getString(R.string.next)
        previous = getString(R.string.previous)
        cancel = getString(R.string.cancel)
        done = getString(R.string.done)
        
        dots_indicator.setDotsClickable(false)
        
        previous_button.text = cancel
        next_button.text = next
    }
    
    private fun buildViewPager() {
        adapter = CategoryFormPageAdapter(supportFragmentManager, maxPage, SubcategoryFormPageType.values() as Array<ICategoryFormType>).also {
            view_pager.adapter = it
        }
        dots_indicator.setViewPager(view_pager)
    }
    
    override fun onClick() {
        previous_button.setOnClickListener {
            
            buttonAntiSpam()
            
            var index = getIndexPage()
            
            if (index <= 0) {
                finish()
                return@setOnClickListener
            }
            
            index = (index - 1)
            
            view_pager.currentItem = index
            
            // If the previous page is the first
            if ((index) == 0) {
                previous_button.text = cancel
                return@setOnClickListener
            }
            
            next_button.text = next
        }
        
        next_button.setOnClickListener {
            
            var index = getIndexPage()
            
            if (index >= (maxPage - 1)) {
                createSubcategory()
                return@setOnClickListener
            }
            
            buttonAntiSpam()
            
            getBaseCategoryFormFragment(adapter?.getItem(index) ?: return@setOnClickListener).let {
                if (!it.isNextValid()) return@setOnClickListener
                if (it.hasExtra()) {
                    val extra = it.getExtra() ?: return@setOnClickListener
                    extras[extra.first] = extra.second
                }
            }
            
            index = (index + 1)
            
            view_pager.currentItem = index
            
            // If the next page is the last
            if ((index) == (maxPage - 1)) {
                next_button.text = done
                return@setOnClickListener
            }
            
            previous_button.text = previous
        }
    }
    
    private fun createSubcategory() {
        val nameSubcategory = extras[EXTRA_INPUT_TEXT_SUBCATEGORY_FRAGMENT] ?: return
        val imageSubcategory = extras[EXTRA_UPLOAD_IMAGE_SUBCATEGORY_FRAGMENT]
        
        next_button.isEnabled = false
        previous_button.isEnabled = false
    
        isRequest = true
        
        viewModel?.createSubcategoryToDatabase(categoryId, nameSubcategory, imageSubcategory)?.observe(this, {
            next_button.isEnabled = true
            previous_button.isEnabled = true
            isRequest = false
            if (!it) return@observe
    
            setResult(RC_REFRESH)
            finish()
        })
    }
    
    private fun getBaseCategoryFormFragment(fragment: Fragment): BaseCategoryFormFragment {
        return (fragment as? BaseCategoryFormFragment) ?: throw ClassCastException("must be BaseCategoryFormFragment")
    }
    
    private fun buttonAntiSpam() {
        next_button.isEnabled = false
        previous_button.isEnabled = false
        
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(BUTTON_ANTI_SPAM_DELAY)
            
            next_button.isEnabled = true
            previous_button.isEnabled = true
            
        }
    }
    
    private fun getIndexPage() = view_pager.currentItem
}