package com.xeross.srapp.controller.main

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.adapter.GameAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.controller.celeste.CelesteActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import com.xeross.srapp.model.SpeedrunType
import kotlinx.android.synthetic.main.activity_celeste.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.activity_game_details_image_header
import kotlinx.android.synthetic.main.activity_main.bottom_navigation_menu


class MainActivity : BaseActivity(), ClickListener<Game> {
    
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main
    
    private lateinit var adapter: GameAdapter
    private val games = ArrayList<Game>()
    private var viewModel: MainViewModel? = null
    
    override fun build() {
        viewModel = (vm as MainViewModel)
        viewModel?.build()
        adapter = GameAdapter(this, games, this).also {
            //activity_main_recyclerview.setRecyclerViewAdapter(it, false)
        }
        getGames()
        
        handleUI()
    }
    
    private fun handleUI() {
        // Reduce size image based on content shape size for create a border
        // Method View::post allows to call the Thread for UI
        activity_game_details_image_header.post {
        
            val image = activity_game_details_image_header
            val imageContent = activity_game_details_content_image_header
        
            // Get new height and width for image
            val borderSize = resources.getDimension(R.dimen.header_image_border_size)
            val height: Int = (imageContent.height - borderSize.toInt())
            val width: Int = (imageContent.width - borderSize.toInt())
        
            // Set image size
            val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(height, width)
            image.layoutParams = params
            image.requestLayout()
        
            // Set image to image header with glide. Also allows rounded image
            // TODO("Images customs")
            Glide.with(this).load(R.drawable.im_celeste)
                .centerCrop() // scale image to fill the entire ImageView
                .circleCrop().into(image)
        }
    
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    
        handleBottomNavigationMenu()
    }
    
    private fun handleBottomNavigationMenu() {
        // unselected the first item (the first item is selected by default when the activity is created)
        bottom_navigation_menu.menu.getItem(0).isCheckable = false
        (bottom_navigation_menu as NavigationBarView).setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            // Test
            when (it.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, CelesteActivity::class.java)
                    startActivity(intent)
                }
            }
            // return false allows don't show color after selected item
            return@OnItemSelectedListener false
        })
    }
    
    private fun getGames() {
        viewModel?.getCeleste(this)?.observe(this, {
            it?.let { game ->
                games.add(game)
                adapter.notifyDataSetChanged()
            }
        })
    }
    
    override fun onClick(o: Game) {
        when (o.idSRC) {
            SpeedrunType.CELESTE -> {
                val intent = Intent(this, CelesteActivity::class.java)
                startActivity(intent)
                return
            }
        }
    }
    
    override fun onLongClick(o: Game) {}
}