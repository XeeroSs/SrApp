package com.xeross.srapp.controller.celeste.level

import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class CelesteRunFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "Any%"
    override fun getLevelName() = "Any%"
    override fun getImageLevelId() = R.drawable.im_celeste_result

    companion object {
        fun getInstance() = CelesteRunFragment()
    }

}