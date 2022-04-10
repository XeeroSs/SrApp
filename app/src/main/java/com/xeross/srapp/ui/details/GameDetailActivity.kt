package com.xeross.srapp.ui.details

import android.content.Intent
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.LeaderBoard
import com.xeross.srapp.data.models.Statistic
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.TimeListener
import com.xeross.srapp.ui.adapters.DividerItemDecoration
import com.xeross.srapp.ui.adapters.LeaderBoardAdapter
import com.xeross.srapp.ui.adapters.StatisticAdapter
import com.xeross.srapp.ui.celeste.CelesteActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_game_details.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GameDetailActivity : BaseActivity(), TimeListener {
    
    override fun getViewModelClass() = GameDetailViewModel::class.java
    override fun getFragmentId() = R.layout.activity_game_details
    
    private lateinit var categoryName: String
    private var categoryId: String? = null
    
    private val subCategories = HashMap<Int, SubCategory>()
    
    private lateinit var statisticAdapter: StatisticAdapter
    private val stats = ArrayList<Statistic>()
    
    private lateinit var leaderBoardAdapter: LeaderBoardAdapter
    private val leaderBoards = ArrayList<LeaderBoard>()
    
    override fun ui() {
        
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
        leaderBoardAdapter = LeaderBoardAdapter(this, leaderBoards, this).also { a ->
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
        
        // TEST val subCategory = subCategories[0]
        val subCategory = SubCategory("id", 0, "Forkasen Cyti", "", Date())
        
        // Method View::post allows to call the Thread for UI
        activity_game_details_image_header.post {
            
            setHeaderImage(subCategory)
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = activity_game_details_recyclerview_ranking.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = bottom_navigation_menu.measuredHeight
        }
        
        setGraphics()
        setStatusBarTransparent()
        setPlaceHolder()
        handleBottomNavigationMenu()
        
        getLevel(subCategory)
    }
    
    private fun loadImageToHeader(subCategory: SubCategory) {
        subCategory.imageURL?.takeIf {
            it.isNotBlank()
        }?.let {
            Glide.with(this).load(it)
                .centerCrop() // scale image to fill the entire ImageView
                .circleCrop().into(activity_game_details_image_header)
            return
        }
        
        Glide.with(this).load(R.drawable.ill_image_upload_amico)
            .centerInside()
            .circleCrop().into(activity_game_details_image_header)
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
    
    // TODO("Graphics")
    private fun setGraphics() {
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
    
    // TODO("Put into base activity")
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
    
    private fun getLevel(subCategory: SubCategory?) {
        if (subCategory == null) return
        getHeader(subCategory)
        loadImageToHeader(subCategory)
    }
    
    /**
     * Reduce size image based on content shape size for create a border
     */
    private fun setHeaderImage(subCategory: SubCategory?) {
        if (subCategory == null) return
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
        loadImageToHeader(subCategory)
    }
    
    private fun getHeader(subCategory: SubCategory) {
        activity_game_details_text_name_level.text = resources.getString(R.string.game_details_header_level_name, subCategory.name)
        activity_game_details_text_category.text = resources.getString(R.string.game_details_header_level_name, categoryName)
    }
    
    override fun setUp() {
        // Get name from intent extra for header
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: "???"
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID)
    }
    
    override fun onClick() {
        activity_game_details_button_previous.setOnClickListener {
        
        }
        
        activity_game_details_button_next.setOnClickListener {
        
        }
    }
    
    override fun onPersonnelBestTime(): Long {
        return 0
    }
    
    override fun onBestTime(): Long {
        return 0
    }
}