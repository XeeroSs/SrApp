package com.xeross.srapp.ui.category.management.category

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseGalleryActivity
import com.xeross.srapp.databinding.ActivityCategoryManagementBinding
import com.xeross.srapp.databinding.DialogDeleteConfirmationBinding
import com.xeross.srapp.ui.settings.manager.SettingViewManager
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.livedata.ResultLiveDataType
import java.util.function.Function


class CategoryManagementActivity : BaseGalleryActivity<ActivityCategoryManagementBinding>() {
    
    // Dialogs
    private var dialogView: DialogDeleteConfirmationBinding? = null
    private var dialog: AlertDialog? = null
    
    private lateinit var categoryId: String
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityCategoryManagementBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass() = CategoryManagementViewModel::class.java
    private lateinit var viewModel: CategoryManagementViewModel
    
    override fun setUp() {
        viewModel = vm as CategoryManagementViewModel
        viewModel.build()
        
        categoryId = intent.getStringExtra(Constants.EXTRA_CATEGORY_ID) ?: run {
            finish()
            return
        }
    }
    
    override fun ui() {
        
        setStatusBarTransparent()
        buildHeader(binding.headerToolbar, binding.headerTitle, R.string.management, 25f)
        
        setUpDialogs()
        
        with(SettingViewManager(this, binding.categoryManagementContent)) {
            
            category(R.string.other)
            
            subcategoryWithIcon(R.string.delete, R.drawable.ic_trash, true, Function { _ ->
                launchDialog()
                return@Function true
            })
            
            build()
        }
        
        viewModel.getCategory(categoryId)?.observe(this) { query ->
            if (query.state == ResultLiveDataType.FAIL) {
                Toast.makeText(this, query.resMessageId, Toast.LENGTH_SHORT).show()
                return@observe
            }
            
            query.result?.let { category ->
                setHeaderImage(category.imageURL)
                loadCategoryName(category.name)
            }
        }
        
    }
    
    private fun loadCategoryName(name: String) {
        binding.categoryManagementName.text = name
    }
    
    /**
     * Reduce size image based on content shape size for create a border
     */
    private fun setHeaderImage(imageURL: String?) {
        val image = binding.categoryManagementImageHeader
        val imageContent = binding.categoryManagementContentImageHeader
        val itemImageHeader = binding.itemImageHeader
        
        // Get new height and width for image
        val borderSize = resources.getDimension(R.dimen.activity_game_details_header_image_border_size)
        val height: Int = (imageContent.height - borderSize.toInt())
        val width: Int = (imageContent.width - borderSize.toInt())
        
        // Set image size
        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(height, width)
        params.marginStart = (params.marginStart + borderSize / 2).toInt()
        params.topMargin = (params.topMargin + borderSize / 2).toInt()
        
        image.layoutParams = params
        image.requestLayout()
        itemImageHeader.layoutParams = params
        itemImageHeader.requestLayout()
        
        // Set image to image header with glide. Also allows rounded image
        loadCategoryImage(imageURL)
    }
    
    private fun loadCategoryImage(imageURL: String?) {
        imageURL?.takeIf {
            it.isNotBlank()
        }?.let {
            Glide.with(this).load(it)
                .centerCrop() // scale image to fill the entire ImageView
                .circleCrop().into(binding.categoryManagementImageHeader)
            return
        }
        
        Glide.with(this).load(R.drawable.ill_image_upload_amico)
            .centerInside()
            .circleCrop().into(binding.categoryManagementImageHeader)
        
    }
    
    private fun setUpDialogs() {
        dialogView = DialogDeleteConfirmationBinding.inflate(LayoutInflater.from(this), null, false).also {
            
            it.cancelButton.setOnClickListener {
                dialog?.dismiss()
            }
            
            dialog = MaterialAlertDialogBuilder(this, R.style.WrapEverythingDialog).setBackground(ColorDrawable(Color.TRANSPARENT)).setCancelable(true).setView(it.root).create()
        }
    }
    
    private fun launchDialog() {
        
        dialogView?.deleteButton?.let { deleteButton ->
            
            deleteButton.isEnabled = false
            
            dialogView?.confirmEditText?.editText?.let { editText ->
                editText.text?.clear()
                
                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    
                    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (text.toString() == "CONFIRM") {
                            deleteButton.isEnabled = true
                            return
                        }
                        deleteButton.isEnabled = false
                    }
                    
                    override fun afterTextChanged(p0: Editable?) {
                    }
                })
            }
            
            deleteButton.setOnClickListener {
                Toast.makeText(this, "Test", Toast.LENGTH_LONG).show()
            }
            
        }
        
        
        dialog?.show()
    }
    
    override fun onClick() {
        binding.itemImageHeader.setOnClickListener {
            openGallery()
        }
    }
    
    override fun getUri(cropImageToUri: Uri) {
        
/*        viewModel.uploadImage(categoryId)?.observe(this) { query ->
            if (query.state == ResultLiveDataType.FAIL) {
                Toast.makeText(this, query.resMessageId, Toast.LENGTH_SHORT).show()
                return@observe
            }
            
            query.result?.let { _ ->
                Glide.with(this).load(cropImageToUri).into(binding.categoryManagementImageHeader)
            }
        }*/
    }
}