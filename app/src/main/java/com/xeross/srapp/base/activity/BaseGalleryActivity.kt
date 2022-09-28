package com.xeross.srapp.base.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.viewbinding.ViewBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.xeross.srapp.R
import com.xeross.srapp.listener.GalleryListener
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
abstract class BaseGalleryActivity<B : ViewBinding> : BaseActivity<B>(), GalleryListener {
    
    private var uri: Uri? = null
    
    companion object {
        private const val PERMISSION_GALLERY = 123
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
    
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun openGallery() {
        
        CropImage.activity().setScaleType(CropImageView.ScaleType.CENTER_CROP)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .setFixAspectRatio(true)
            .setAspectRatio(1, 1)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
        
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) return
        val result = CropImage.getActivityResult(data)
        if (resultCode == Activity.RESULT_OK) {
            uri = result.uri.also {
                getUri(it)
            }
            return
        }
        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            val error = result.error
            error.printStackTrace()
        }
    }
    
    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun openRationaleForGallery() {
        AlertDialog.Builder(this).setTitle(R.string.permission)
            .setMessage(R.string.message_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_GALLERY)
            }.create().show()
    }
    
}