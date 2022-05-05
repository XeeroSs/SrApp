package com.xeross.srapp.ui.categoryform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.xeross.srapp.R
import com.xeross.srapp.ui.categoryform.fragment.base.BaseCategoryFormFragment

class CategoryFormIllustrationFragment : BaseCategoryFormFragment() {
    
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
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
        val view = inflater.inflate(R.layout.cell_form_image, container, false)
        
        buildUI(view)
        
        return view
    }
    
    private fun buildUI(view: View) {
        val titleView = view.findViewById<TextView>(R.id.form_title)
        titleView.text = title
        
        val subtitleView = view.findViewById<TextView>(R.id.form_subtitle)
        subtitleView.text = subtitle
        
        resImage?.let {
            val illustrationView = view.findViewById<ImageView>(R.id.form_illustration)
            illustrationView.setBackgroundResource(it)
        }
    }
    
}