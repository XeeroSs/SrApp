package com.xeross.srapp.controller.celeste

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.xeross.srapp.R
import com.xeross.srapp.adapter.PageAdapter
import com.xeross.srapp.base.BaseActivityOAuth
import com.xeross.srapp.controller.celeste.level.*
import kotlinx.android.synthetic.main.activity_celeste.*

class CelesteActivity : BaseActivityOAuth() {
    
    override fun getFragmentId() = R.layout.activity_celeste
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set image to image header with glide. Also allows rounded image
        Glide.with(this).load(R.drawable.im_celeste)
            .transform(RoundedCorners(8)).into(activity_game_details_image_header)
    }
}