package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment

class CelestialResortFragment : BaseFragment() {

    override fun getFragmentId() = R.layout.fragment_celestial_resort
    override fun getSheetsName() = "Celestial Resort"

    companion object {
        fun getInstance() = CelestialResortFragment()
    }

}