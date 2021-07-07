package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class TheSummitFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_the_summit
    override fun getSheetsName() = "The Summit"

    companion object {
        fun getInstance() = TheSummitFragment()
    }

}