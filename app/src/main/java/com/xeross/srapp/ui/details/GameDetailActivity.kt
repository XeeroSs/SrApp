package com.xeross.srapp.ui.details

import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailActivity : BaseActivity() {
    
    override fun getViewModelClass() = GameDetailViewModel::class.java
    override fun getFragmentId() = R.layout.activity_game_details
    
    private lateinit var categoryName: String
    
    override fun ui() {
        // Header
        header()
    }
    
    override fun build() {
        // Get name from intent extra for header
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: "???"
    }
    
    override fun onClick() {
    
    }
    
    private fun header() {
        // Texts
        activity_game_details_text_name_level.text = getString(R.string.game_details_header_level_name, categoryName)
        activity_game_details_text_category.text = getString(R.string.game_details_header_category_name, categoryName)
        activity_game_details_text_time.text = getString(R.string.game_details_header_time_personnel_best, categoryName)
        
        // Image
        
    }
    
    
}