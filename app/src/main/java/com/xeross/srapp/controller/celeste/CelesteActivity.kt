package com.xeross.srapp.controller.celeste

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.adapter.DividerItemDecoration
import com.xeross.srapp.adapter.LeaderBoardAdapter
import com.xeross.srapp.adapter.StatisticAdapter
import com.xeross.srapp.base.BaseActivityOAuth
import com.xeross.srapp.injection.ViewModelFactory
import com.xeross.srapp.model.LeaderBoard
import com.xeross.srapp.model.Statistic
import kotlinx.android.synthetic.main.activity_celeste.*
import kotlinx.android.synthetic.main.activity_celeste.bottom_navigation_menu
import kotlinx.android.synthetic.main.activity_main.*

class CelesteActivity : BaseActivityOAuth() {
    
    override fun getFragmentId() = R.layout.activity_celeste
    
    override fun getViewModelClass() = CelesteViewModel::class.java
    
    fun getSheetsName() = "Forsaken City"
    
    private lateinit var statisticAdapter: StatisticAdapter
    private val stats = ArrayList<Statistic>()
    
    private lateinit var leaderBoardAdapter: LeaderBoardAdapter
    private val leaderBoards = ArrayList<LeaderBoard>()
    
    private var viewModel: CelesteViewModel? = null
    
    @SuppressWarnings("unchecked")
    private fun configureViewModel(): CelesteViewModel {
        val factory = ViewModelFactory(this)
        return ViewModelProvider(this, factory).get(CelesteViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
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
        // TODO("Get my own time from sheets")
        leaderBoardAdapter = LeaderBoardAdapter(this, leaderBoards, 56, 60).also { a ->
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
        
        credential?.let { credential ->
            viewModel = configureViewModel().also {
                it.build(getSheetsName(), credential, this)
            }
            
            getAuthorization()?.observe(this, {
                if (it == null || !it) return@observe
                getLeaderBoards()
            })
        }
        
        handleUI()
    }
    
    override fun build() {
    }
    
    private fun handleUI() {
        
        // Method View::post allows to call the Thread for UI
        activity_game_details_image_header.post {
            
            setHeaderImage()
            
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = activity_game_details_recyclerview_ranking.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = bottom_navigation_menu.measuredHeight
        }
        
        setGraphics()
        
        handleStatusBar()
        
        setPlaceHolder()
        
        handleBottomNavigationMenu()
    }
    
    /**
     * Reduce size image based on content shape size for create a border
     */
    private fun setHeaderImage() {
        val image = activity_game_details_image_header
        val imageContent = activity_game_details_content_image_header
        
        // Get new height and width for image
        val borderSize = resources.getDimension(R.dimen.activity_game_details_header_image_border_size)
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
    
    private fun setPlaceHolder() {
        val stats = activity_game_details_text_your_stats
        val ranking = activity_game_details_text_ranking
        
        // TODO("Use cache with sharedPreferences")
        val resStats = resources.getString(R.string.game_details_text_stats, "all runs")
        val resRanking = resources.getString(R.string.game_details_text_ranking, "global")
        
        stats.text = resStats
        ranking.text = resRanking
    }
    
    private fun handleStatusBar() {
        // TODO("Add in base activity")
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
    
    private fun setGraphics() {
        // Graphics
        /*        val graphic = activity_game_details_graphic as GraphView
                val colorGraphic = resources.getColor(R.color.blue_graphic)
                val series = LineGraphSeries(arrayOf(DataPoint(0.0, 1.0),
                    DataPoint(1.0, 5.0),
                    DataPoint(2.0, 3.0),
                    DataPoint(3.0, 2.0),
                    DataPoint(4.0, 6.0)))
                graphic.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
                graphic.gridLabelRenderer.isVerticalLabelsVisible = false
                graphic.gridLabelRenderer.isHorizontalLabelsVisible = false
                graphic.gridLabelRenderer.labelHorizontalHeight = 2
                series.backgroundColor = colorGraphic
                series.isDrawBackground = true
                graphic.addSeries(series)*/
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
    
    private fun getLeaderBoards() {
        viewModel?.getLeaderBoard()?.observe(this, { leaderBoardOrNull ->
            leaderBoardOrNull?.let { leaderBoards ->
                this.leaderBoards.addAll(leaderBoards)
                leaderBoardAdapter.notifyDataSetChanged()
            }
        })
    }
}