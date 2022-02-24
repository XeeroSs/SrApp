package com.xeross.srapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter<F : Fragment>(context: FragmentActivity, private val fragments: ArrayList<F>) : FragmentStateAdapter(context) {
    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}