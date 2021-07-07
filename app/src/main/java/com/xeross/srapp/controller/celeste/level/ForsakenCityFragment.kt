package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class ForsakenCityFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_forsaken_city
        override fun getSheetsName() = "Forsaken City"

    companion object {
        fun getInstance() = ForsakenCityFragment()
    }

}