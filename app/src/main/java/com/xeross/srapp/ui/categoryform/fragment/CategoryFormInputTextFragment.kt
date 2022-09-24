package com.xeross.srapp.ui.categoryform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.xeross.srapp.R
import com.xeross.srapp.databinding.CellFormEditTextBinding
import com.xeross.srapp.ui.categoryform.fragment.base.BaseCategoryFormFragment
import com.xeross.srapp.utils.extensions.UIExtensions.error

class CategoryFormInputTextFragment : BaseCategoryFormFragment<CellFormEditTextBinding>() {
    
    companion object {
        
        private const val EXTRA_KEY = "extraKey"
        private const val KEY_TITLE = "title"
        private const val KEY_SUBTITLE = "subtitle"
        private const val KEY_INPUT_TEXT_LABEL = "input_text_label"
        
        fun newInstance(extraKey: String, resTitle: Int, resSubTitle: Int, resInputTextLabel: Int): Fragment {
            
            val fragment = CategoryFormInputTextFragment()
            val args = Bundle()
            args.putString(EXTRA_KEY, extraKey)
            args.putInt(KEY_TITLE, resTitle)
            args.putInt(KEY_SUBTITLE, resSubTitle)
            args.putInt(KEY_INPUT_TEXT_LABEL, resInputTextLabel)
            fragment.arguments = args
            
            return fragment
        }
    }
    
    private var title: String? = null
    private var subtitle: String? = null
    private var inputTextLabel: String? = null
    private var extra: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        arguments?.let { bundle ->
            extra = bundle.getString(EXTRA_KEY)
            title = getString(bundle.getInt(KEY_TITLE))
            subtitle = getString(bundle.getInt(KEY_SUBTITLE))
            inputTextLabel = getString(bundle.getInt(KEY_INPUT_TEXT_LABEL))
        }
    }
    
    override fun getExtra(): Pair<String, String>? {
        val inputText = binding.formEditText
        val editText = inputText.editText ?: return null
        return Pair(extra ?: return null, editText.text.toString())
    }
    
    override fun hasExtra() = true
    
    override fun isNextValid(): Boolean {
        val inputText = binding.formEditText
        val editText = inputText.editText ?: return false
        if (editText.text.isBlank()) {
            inputText.error(requireContext(), R.string.field_is_required)
            return false
        }
        inputText.error = null
        return true
    }
    
    override fun attachViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return CellFormEditTextBinding.inflate(inflater, container, false)
    }
    
    override fun setup() {
        buildUI()
    }
    
    private fun buildUI() {
        val titleView = binding.include.formTitle
        titleView.text = title
        
        val subtitleView = binding.include.formSubtitle
        subtitleView.text = subtitle
        
        val inputTextLabelView = binding.formEditTextLabel
        inputTextLabelView.text = inputTextLabel
        
    }
    
}