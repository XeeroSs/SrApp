package com.xeross.srapp.listener

import android.net.Uri

interface GalleryListener {
    fun getUri(cropImageToUri: Uri)
}