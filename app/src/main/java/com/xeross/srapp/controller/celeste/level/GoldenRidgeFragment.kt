package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class GoldenRidgeFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "Golden Ridge"
    override fun getLevelName() = "Golden Ridge"
    override fun getImageLevelId() = R.drawable.im_celeste_level_4

    companion object {
        fun getInstance() = GoldenRidgeFragment()
    }

}