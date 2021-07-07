package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class OldCityFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_old_city
    override fun getSheetsName() = "Old City"

    companion object {
        fun getInstance() = OldCityFragment()
    }

}