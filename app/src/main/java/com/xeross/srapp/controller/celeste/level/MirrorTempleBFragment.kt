package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class MirrorTempleBFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_mirror_temple_b
    override fun getSheetsName() = "Mirror Temple 5B"

    companion object {
        fun getInstance() = MirrorTempleBFragment()
    }

}