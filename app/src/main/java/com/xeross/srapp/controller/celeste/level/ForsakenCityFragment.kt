package com.xeross.srapp.controller.celeste.level

import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class ForsakenCityFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "Forsaken City"
    override fun getLevelName() = "Forsaken City"
    override fun getImageLevelId() = R.drawable.im_celeste_level_1

    companion object {
        fun getInstance() = ForsakenCityFragment()
    }

}