package com.xeross.srapp.ui.categoryform.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xeross.srapp.ui.categoryform.interfaces.ICategoryFormType

// DotsIndicator don't work with ViewPager2..
class CategoryFormPageAdapter(fragmentManager: FragmentManager, private val maxPage: Int, private val pages: Array<ICategoryFormType>) : FragmentPagerAdapter(fragmentManager) {
    
    override fun getCount() = maxPage
    
    override fun getItem(position: Int): Fragment {
        return pages.let { page ->
            page.find { it.getFragmentPosition() == position }?.getFragment() ?: page[0].getFragment()
        }
    }
}