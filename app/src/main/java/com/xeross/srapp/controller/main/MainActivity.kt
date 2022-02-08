package com.xeross.srapp.controller.main

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.adapter.DividerItemDecoration
import com.xeross.srapp.adapter.RankingAdapter
import com.xeross.srapp.adapter.StatisticAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.controller.celeste.CelesteActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import com.xeross.srapp.model.Ranking
import com.xeross.srapp.model.SpeedrunType
import com.xeross.srapp.model.Statistic
import kotlinx.android.synthetic.main.activity_celeste.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.activity_game_details_image_header
import kotlinx.android.synthetic.main.activity_main.bottom_navigation_menu


class MainActivity : BaseActivity(), ClickListener<Game> {
    
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main
    
    private lateinit var statisticAdapter: StatisticAdapter
    private val stats = ArrayList<Statistic>()
    
    private lateinit var rankingAdapter: RankingAdapter
    private val rankings = ArrayList<Ranking>()
    
    private var viewModel: MainViewModel? = null
    
    override fun build() {
        viewModel = (vm as MainViewModel)
        viewModel?.build()
        statisticAdapter = StatisticAdapter(this, stats).also { a ->
            activity_game_details_recyclerview_stats.let {
                val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                //    val dd = DividerItemDecoration(this, 20, R.drawable.shape_divider, LinearLayoutManager.HORIZONTAL)
                val dd = DividerItemDecoration(this, 20, R.drawable.shape_divider_horizontal, LinearLayoutManager.HORIZONTAL)
                it.addItemDecoration(dd)
                //   it.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        rankingAdapter = RankingAdapter(this, rankings).also { a ->
            activity_game_details_recyclerview_ranking.let {
                val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                //    val dd = DividerItemDecoration(this, 20, R.drawable.shape_divider, LinearLayoutManager.HORIZONTAL)
                val dd = DividerItemDecoration(this, 0, R.drawable.shape_divider_vertical, LinearLayoutManager.VERTICAL)
                it.addItemDecoration(dd)
                //   it.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
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
        // TEST
        stats.add(Statistic("11", "Total des runs"))
        stats.add(Statistic("12:322", "Best"))
        stats.add(Statistic("45:936", "Worst"))
        stats.add(Statistic("42:152", "Average"))
        statisticAdapter.notifyDataSetChanged()
        
        rankings.add(Ranking("Zkad", "23:23.222", R.drawable.im_celeste_level_1, 1))
        rankings.add(Ranking("Marlin", "24:33.124", R.drawable.im_celeste_result, 2))
        rankings.add(Ranking("Buhbai", "25:42.568", R.drawable.im_celeste_level_5, 3))
        rankingAdapter.notifyDataSetChanged()

/*        viewModel?.getCeleste(this)?.observe(this, {
            it?.let { game ->
                games.add(game)
                adapter.notifyDataSetChanged()
            }
        })*/
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