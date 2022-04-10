package com.xeross.srapp.ui.categoryform.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xeross.srapp.ui.categoryform.types.CategoryFormPageType

// DotsIndicator don't work with ViewPager2..
class CategoryFormPageAdapter(fragmentManager: FragmentManager, private val maxPage: Int) : FragmentPagerAdapter(fragmentManager) {
    
    override fun getCount() = maxPage
    
    override fun getItem(position: Int): Fragment {
        return CategoryFormPageType.values().let { page ->
            page.find { it.position == position }?.fragment ?: page[0].fragment
        }
    }
}