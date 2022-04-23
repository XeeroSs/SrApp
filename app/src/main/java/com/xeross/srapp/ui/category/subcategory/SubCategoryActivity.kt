package com.xeross.srapp.ui.category.subcategory

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.components.DividerItemDecoration
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.types.StatisticType
import com.xeross.srapp.listener.DataListener
import com.xeross.srapp.listener.TimeListener
import com.xeross.srapp.ui.adapters.StatisticAdapter
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity.Companion.RC_REFRESH
import com.xeross.srapp.ui.celeste.CelesteActivity
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_NAME
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_URL
import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import kotlinx.android.synthetic.main.activity_subcategory.*
import kotlinx.android.synthetic.main.dialog_add_time.*
import kotlinx.android.synthetic.main.dialog_add_time.view.*
import java.util.*
import kotlin.collections.ArrayList


class SubCategoryActivity : BaseActivity(), TimeListener, DataListener<SubCategory> {
    
    companion object {
        private const val TAG = "GameDetailActivityTAG"
    }
    
    override fun getViewModelClass() = SubcategoryViewModel::class.java
    override fun getFragmentId() = R.layout.activity_subcategory
    
    // Dialogs
    private var dialogView: View? = null
    private var dialog: AlertDialog? = null
    
    private lateinit var categoryName: String
    private lateinit var categoryId: String
    private lateinit var subcategory: SubCategory
    
    private val statistics = ArrayList<Pair<StatisticType, String>>()
    
    private var bestInMilliseconds = 0L
    private var worstInMilliseconds = 0L
    private var averageInMilliseconds = 0L
    private var total = 0
    
    private var index = 0
    
    private val times = ArrayList<Long>()
    
    private var hoursPicker: NumberPicker? = null
    private var minutesPicker: NumberPicker? = null
    private var secondsPicker: NumberPicker? = null
    private var millisecondsPicker: NumberPicker? = null
    
    private var statisticAdapter: StatisticAdapter? = null

/*    private lateinit var leaderBoardAdapter: LeaderBoardAdapter
    private val leaderBoards = ArrayList<LeaderBoard>()*/
    
    private var viewModel: SubcategoryViewModel? = null
    
    override fun ui() {
        statisticAdapter = StatisticAdapter(this, statistics).also { a ->
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
/*        leaderBoardAdapter = LeaderBoardAdapter(this, leaderBoards, this).also { a ->
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
        }*/
        
        // Method View::post allows to call the Thread for UI
        activity_game_details_image_header.post {
            
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = activity_game_details_recyclerview_ranking.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = bottom_navigation_menu.measuredHeight
        }
        
        setGraphics()
        setStatusBarTransparent()
        setPlaceHolder()
        handleBottomNavigationMenu()
        
        setUpDialogs()
    }
    
    private fun setUpDialogs() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_time, null, false).also {
            hoursPicker = it.findViewById<NumberPicker>(R.id.hour_picker)?.setUpNumberPickers(999) ?: return
            minutesPicker = it.findViewById<NumberPicker>(R.id.minute_picker)?.setUpNumberPickers(59)?.also { np ->
                np.setFormatter { digit ->
                    if (digit < 10) return@setFormatter "0$digit" else return@setFormatter digit.toString()
                }
            } ?: return
            secondsPicker = it.findViewById<NumberPicker>(R.id.second_picker)?.setUpNumberPickers(59)?.also { np ->
                np.setFormatter { digit ->
                    if (digit < 10) return@setFormatter "0$digit" else return@setFormatter digit.toString()
                }
            } ?: return
            millisecondsPicker = it.findViewById<NumberPicker>(R.id.millisecond_picker)?.setUpNumberPickers(999)?.also { np ->
                np.setFormatter { digit ->
                    if (digit < 10) return@setFormatter "00$digit" else if (digit < 100) return@setFormatter "0$digit" else return@setFormatter digit.toString()
                }
            } ?: return
            
            it.findViewById<MaterialButton>(R.id.dismiss_button)?.setOnClickListener { _ ->
                resetDialogPicker()
                dialog?.dismiss()
            }
            
            it.findViewById<MaterialButton>(R.id.submit_button)?.setOnClickListener { _ ->
                // The value inputted by the user is not updated if the user is still in the text input of a NumberPicker (In the case where the user himself inputs the value of the NumberPicker with his keyboard).
                // However, disabling it before getting its value allows it to be updated.
                toggleNumberPickers(false)
                addTime()
                toggleNumberPickers(true)
            }
            
            dialog = MaterialAlertDialogBuilder(this, R.style.WrapEverythingDialog).setBackground(ColorDrawable(android.graphics.Color.TRANSPARENT)).setCancelable(true).setView(it).create()
        }
    }
    
    private fun toggleNumberPickers(toggle: Boolean) {
        hoursPicker?.isEnabled = toggle
        minutesPicker?.isEnabled = toggle
        secondsPicker?.isEnabled = toggle
        millisecondsPicker?.isEnabled = toggle
    }
    
    private fun addTime() {
        dialogView?.submit_button?.isEnabled = false
        getTimeFromDialog(hoursPicker!!.value, minutesPicker!!.value, secondsPicker!!.value, millisecondsPicker!!.value)?.observe(this, { time ->
            dialogView?.submit_button?.isEnabled = true
            if (time == null) return@observe
            setResult(RC_REFRESH)
            times.add(time)
            refresh()
            resetDialogPicker()
            dialog?.dismiss()
        })
    }
    
    private fun refresh() {
        setHeaderImage()
        getStats()
        getHeader()
    }
    
    private fun resetDialogPicker() {
        hoursPicker?.value = 0
        minutesPicker?.value = 0
        secondsPicker?.value = 0
        millisecondsPicker?.value = 0
    }
    
    private fun NumberPicker.setUpNumberPickers(max: Int): NumberPicker {
        return this.apply {
            this.maxValue = max
            this.minValue = 0
        }
    }
    
    private fun launchDialog() {
        dialog?.show()
    }
    
    // TODO("Get time from dialog")
    private fun getTimeFromDialog(hours: Int, minutes: Int, seconds: Int, milliseconds: Int): LiveData<Long?>? {
        Log.i(TAG, "$hours:$minutes:$seconds.$milliseconds")
        // (milliseconds) + (seconds * 1000) + (minutes * 60 * 1000) + (hours * 60² * 1000)
        val timeToMilliseconds = (milliseconds) + (seconds * 1000) + (minutes * 60 * 1000) + (hours * 60 * 60 * 1000L)
        
        val isBest = bestInMilliseconds > timeToMilliseconds
/*        val simpleDateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")*/
        return viewModel?.saveTime(this, categoryId, subcategory.id, timeToMilliseconds, isBest)
    }
    
    private fun loadImageToHeader() {
        subcategory.imageURL?.takeIf {
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
    
    private fun getStats() {
        statistics.clear()
        times.getBestToMilliseconds().let {
            bestInMilliseconds = it
            statistics.add(Pair(StatisticType.BEST, it.toFormatTime()))
        }
        times.getAverageToMilliseconds().let {
            averageInMilliseconds = it
            statistics.add(Pair(StatisticType.AVERAGE, it.toFormatTime()))
        }
        times.getWorstToMilliseconds().let {
            worstInMilliseconds = it
            statistics.add(Pair(StatisticType.WORST, it.toFormatTime()))
        }
        times.size.let {
            total = it
            statistics.add(Pair(StatisticType.TOTAL, it.toString()))
        }
        statisticAdapter?.notifyDataSetChanged()
    }
    
    override fun notifyDataChanged(data: SubCategory) {
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
        loadImageToHeader()
    }
    
    private fun getHeader() {
        activity_game_details_text_name_level.text = resources.getString(R.string.game_details_header_level_name, subcategory.name)
        activity_game_details_text_category.text = resources.getString(R.string.game_details_header_level_name, categoryName)
        activity_game_details_text_time.text = resources.getString(R.string.game_details_header_level_name, bestInMilliseconds.toFormatTime())
    }
    
    override fun setUp() {
        viewModel = (vm as SubcategoryViewModel?)
        viewModel?.build()
        
        // Get name from intent extra for header
        val subcategoryName = intent.getStringExtra(EXTRA_SUBCATEGORY_NAME) ?: "???"
        val subcategoryURL = intent.getStringExtra(EXTRA_SUBCATEGORY_URL) ?: "???"
        val subcategoryId = intent.getStringExtra(EXTRA_SUBCATEGORY_ID) ?: run {
            finish()
            return
        }
        
        subcategory = SubCategory(id = subcategoryId, name = subcategoryName, imageURL = subcategoryURL)
        
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: "???"
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: run {
            finish()
            return
        }
        
        // Get data
        viewModel?.let {
            it.getSubCategoryTimes(categoryId, subcategoryId).observe(this, { time ->
                times.addAll(time)
                refresh()
            })
        }
    }
    
    override fun onClick() {
        activity_game_details_button_add_your_stats.setOnClickListener {
            launchDialog()
        }
    }
    
    override fun onPersonnelBestTime(): Long {
        return bestInMilliseconds
    }
    
    override fun onBestTime(): Long {
        return bestInMilliseconds
    }
}