package com.xeross.srapp.ui.categoryform.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.xeross.srapp.base.fragment.BaseGalleryFragment
import com.xeross.srapp.databinding.CellFormUploadImageBinding


class CategoryFormUploadImageFragment : BaseGalleryFragment<CellFormUploadImageBinding>() {
    
    
    companion object {
        
        private const val EXTRA_KEY = "extraKey"
        private const val KEY_TITLE = "title"
        private const val KEY_SUBTITLE = "subtitle"
        private const val KEY_RES_IMAGE = "res_image"
        
        fun newInstance(extraKey: String, resTitle: Int, resSubTitle: Int, resImage: Int): Fragment {
            
            val fragment = CategoryFormUploadImageFragment()
            val args = Bundle()
            args.putString(EXTRA_KEY, extraKey)
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
    private var extra: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        arguments?.let { bundle ->
            extra = bundle.getString(EXTRA_KEY)
            title = getString(bundle.getInt(KEY_TITLE))
            subtitle = getString(bundle.getInt(KEY_SUBTITLE))
            resImage = bundle.getInt(KEY_RES_IMAGE)
        }
    }
    
    private var uri: Uri? = null
    
    override fun getExtra(): Pair<String, String>? {
        return Pair(extra ?: return null, uri.toString())
    }
    
    override fun hasExtra() = true
    
    override fun isNextValid() = true
    
    override fun attachViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return CellFormUploadImageBinding.inflate(inflater, container, false)
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
            val illustrationView = binding.formUploadImage
            illustrationView.setBackgroundResource(it)
        }
        
        binding.formUploadImageCard.setOnClickListener {
            openGallery()
        }
        
    }
    
    override fun getUri(cropImageToUri: Uri) {
        uri = cropImageToUri
        context?.let { Glide.with(it).load(cropImageToUri).into(binding.formUploadImage) }
    }
    
}