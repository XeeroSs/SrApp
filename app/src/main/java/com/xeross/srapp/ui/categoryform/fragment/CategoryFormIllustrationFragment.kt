package com.xeross.srapp.ui.categoryform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.xeross.srapp.databinding.CellFormImageBinding
import com.xeross.srapp.ui.categoryform.fragment.base.BaseCategoryFormFragment

class CategoryFormIllustrationFragment : BaseCategoryFormFragment<CellFormImageBinding>() {
    
    companion object {
        
        private const val KEY_TITLE = "title"
        private const val KEY_SUBTITLE = "subtitle"
        private const val KEY_RES_IMAGE = "res_image"
        
        fun newInstance(resTitle: Int, resSubTitle: Int, resImage: Int): Fragment {
            
            val fragment = CategoryFormIllustrationFragment()
            val args = Bundle()
            args.putInt(KEY_TITLE, resTitle)
            args.putInt(KEY_SUBTITLE, resSubTitle)
            args.putInt(KEY_RES_IMAGE, resImage)
            fragment.arguments = args
            
            return fragment
        }
    }
    
    private var title: String? = null
    private var subtitle: String? = null
    private var resImage: Int? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        arguments?.let { bundle ->
            title = getString(bundle.getInt(KEY_TITLE))
            subtitle = getString(bundle.getInt(KEY_SUBTITLE))
            resImage = bundle.getInt(KEY_RES_IMAGE)
        }
    }
    
    override fun getExtra() = null
    
    override fun hasExtra() = false
    
    override fun isNextValid() = true
    
    override fun attachViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return CellFormImageBinding.inflate(inflater, container, false)
    }
    
    override fun setup() {
        buildUI()
    }
    
    private fun buildUI() {
        val titleView = binding.include.formTitle
        titleView.text = title
        
        val subtitleView = binding.include.formSubtitle
        subtitleView.text = subtitle
        
        resImage?.let {
            val illustrationView = binding.formIllustration
            illustrationView.setBackgroundResource(it)
        }
    }
    
}