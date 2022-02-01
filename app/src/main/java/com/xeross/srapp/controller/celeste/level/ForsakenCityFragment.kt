package com.xeross.srapp.controller.celeste.level

import android.os.Bundle
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseFragment
import com.xeross.srapp.controller.celeste.CelesteActivity

class ForsakenCityFragment : BaseFragment() {
    
    override fun getFragmentId() = R.layout.fragment_celeste_run
    override fun getSheetsName() = "Forsaken City"
    override fun getLevelName() = "Forsaken City"
    override fun getImageLevelId() = R.drawable.im_celeste_level_1
    
    companion object {
        fun getInstance() = ForsakenCityFragment()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        (this.activity as? CelesteActivity)?.let { activity ->
            
            activity.credential?.let { credential ->
                viewModel = configureViewModel().also {
                    it.build(getSheetsName(), credential, activity)
                }
                
                activity.getAuthorization()?.observe(this, {
                    if (it == null || !it) returnTransition
                    getDataWithGoogleSheet()
                })
            }
        }
    }
    
    private fun getDataWithGoogleSheet() {
        activity?.let {
            viewModel?.getAnyWRLeaderBoard()?.observe(it, {
                // TODO("")
            })
        }
    }
    
    
}