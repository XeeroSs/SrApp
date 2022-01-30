package com.xeross.srapp.controller.celeste.level

import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class TheSummitFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "The Summit"
    override fun getLevelName() = "The Summit"
    override fun getImageLevelId() = R.drawable.im_celeste_level_7

    companion object {
        fun getInstance() = TheSummitFragment()
    }

}