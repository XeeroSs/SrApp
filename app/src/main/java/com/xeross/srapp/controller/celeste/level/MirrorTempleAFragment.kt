package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class MirrorTempleAFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "Mirror Temple 5A (Cassette)"
    override fun getLevelName() = "Mirror Temple 5A"
    override fun getImageLevelId() = R.drawable.im_celeste_level_5

    companion object {
        fun getInstance() = MirrorTempleAFragment()
    }

}