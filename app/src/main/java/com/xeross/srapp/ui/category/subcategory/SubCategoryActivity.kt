package com.xeross.srapp.ui.category.subcategory

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.NumberPicker
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.components.DividerItemDecoration
import com.xeross.srapp.components.NumberPickerBuilder
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.data.models.types.StatisticType
import com.xeross.srapp.databinding.ActivitySubcategoryBinding
import com.xeross.srapp.databinding.DialogAddTimeBinding
import com.xeross.srapp.listener.TimeListener
import com.xeross.srapp.ui.adapters.StatisticAdapter
import com.xeross.srapp.ui.category.management.CategoryManagementActivity
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity.Companion.RC_REFRESH
import com.xeross.srapp.ui.category.subcategory.types.TimeSortType
import com.xeross.srapp.ui.settings.types.SettingType
import com.xeross.srapp.ui.times.TimesActivity
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_NAME
import com.xeross.srapp.utils.Constants.EXTRA_SUBCATEGORY_URL
import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTimeWithoutMilliseconds
import com.xeross.srapp.utils.livedata.ResultLiveDataType


class SubCategoryActivity : BaseActivity<ActivitySubcategoryBinding>(), TimeListener {
    
    companion object {
        private const val TAG = "GameDetailActivityTAG"
    }
    
    override fun getViewModelClass() = SubcategoryViewModel::class.java
    
    override fun attachViewBinding(): ViewBinding {
        return ActivitySubcategoryBinding.inflate(layoutInflater)
    }
    
    // Dialogs
    private var dialogView: DialogAddTimeBinding? = null
    private var dialog: AlertDialog? = null
    
    private lateinit var categoryName: String
    private lateinit var categoryId: String
    private lateinit var subcategory: SubCategory
    
    private val statistics = ArrayList<Pair<StatisticType, String>>()
    private val numberPickers = ArrayList<NumberPicker>()
    
    private var bestOnAllRunsInMilliseconds = 0L
    
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
    
    private var currentTimeSort = TimeSortType.MONTH
    
    private var viewModel: SubcategoryViewModel? = null
    
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RC_REFRESH) {
            setResult(RC_REFRESH)
            getTimes()
        }
    }
    
    private fun onClickTimeMenu(view: View) {
        showMenu(view, R.menu.popup_times_menu).setOnMenuItemClickListener { menu ->
            TimeSortType.values().find { menu.itemId == it.resId }?.let { type ->
                currentTimeSort = type
                setPlaceHolder()
                getTimes()
            }
            return@setOnMenuItemClickListener true
        }
    }
    
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.subcategory_menu_settings -> {
            val intent = Intent(this, CategoryManagementActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.subcategory_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun ui() {
        
        setPlaceHolder()
        
        setSupportActionBar(binding.headerSubcategoryToolbar)
        supportActionBar?.title = null
        
        binding.headerSubcategoryToolbar.setNavigationOnClickListener {
            finish()
        }
        
        statisticAdapter = StatisticAdapter(this, statistics).also { a ->
            binding.activityGameDetailsRecyclerviewStats.let {
                val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                val dd = DividerItemDecoration(this, 20, R.drawable.shape_divider_horizontal, LinearLayoutManager.HORIZONTAL)
                it.addItemDecoration(dd)
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
        // Method View::post allows to call the Thread for UI
        binding.activityGameDetailsImageHeader.post {
            
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = binding.activityGameDetailsRecyclerviewRanking.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = binding.menu.bottomNavigationMenu.measuredHeight
        }
        
        setGraphics()
        setStatusBarTransparent()
        setPlaceHolder()
        buildBottomNavigationMenu(binding.menu.bottomNavigationMenu)
        
        setUpDialogs()
    }
    
    private fun setUpDialogs() {
        dialogView = DialogAddTimeBinding.inflate(LayoutInflater.from(this), null, false).also {
            hoursPicker = NumberPickerBuilder(it.hourPicker).max(999).min().build().add()
            minutesPicker = NumberPickerBuilder(it.minutePicker).max(59).min().format { d -> if (d < 10) return@format "0$d" else return@format d.toString() }.build().add()
            secondsPicker = NumberPickerBuilder(it.secondPicker).max(59).min().format { d -> if (d < 10) return@format "0$d" else return@format d.toString() }.build().add()
            millisecondsPicker = NumberPickerBuilder(it.millisecondPicker).max(999).min().format { d -> if (d < 10) return@format "00$d" else if (d < 100) return@format "0$d" else return@format d.toString() }.build().add()
            
            it.dismissButton.setOnClickListener { _ ->
                resetDialogPicker()
                dialog?.dismiss()
            }
            
            it.submitButton.setOnClickListener { _ ->
                // The value inputted by the user is not updated if the user is still in the text input of a NumberPicker (In the case where the user himself inputs the value of the NumberPicker with his keyboard).
                // However, disabling it before getting its value allows it to be updated.
                toggleNumberPickers(false)
                addTime()
                toggleNumberPickers(true)
            }
            
            dialog = MaterialAlertDialogBuilder(this, R.style.WrapEverythingDialog).setBackground(ColorDrawable(android.graphics.Color.TRANSPARENT)).setCancelable(true).setView(it.root).create()
        }
    }
    
    
    private fun NumberPicker.add(): NumberPicker {
        numberPickers.add(this)
        return this
    }
    
    private fun toggleNumberPickers(toggle: Boolean) {
        numberPickers.forEach { it.isEnabled = toggle }
    }
    
    private fun addTime() {
        dialogView?.submitButton?.isEnabled = false
        
        // (milliseconds) + (seconds * 1000) + (minutes * 60 * 1000) + (hours * 60² * 1000)
        val timeToMilliseconds = (millisecondsPicker!!.value + 0L) + (secondsPicker!!.value * 1000L) + (minutesPicker!!.value * 60 * 1000L) + (hoursPicker!!.value * 60L * 60L * 1000L)
        
        val subCategoryId = subcategory.id
        
        with(viewModel ?: return) {
            
            saveTime(categoryId, subCategoryId, timeToMilliseconds)?.observe(this@SubCategoryActivity) { result ->
                dialogView?.submitButton?.isEnabled = true
                if (result.state != ResultLiveDataType.SUCCESS) return@observe
                
                val isBest = if (bestInMilliseconds <= 0) true else bestInMilliseconds > timeToMilliseconds
                
                if (isBest) saveTimeIfBestToSubcategoryDocument(categoryId, subCategoryId, timeToMilliseconds)?.observe(this@SubCategoryActivity, {})
                updateUserProfile(isBest)?.observe(this@SubCategoryActivity, {})
                
                setResult(RC_REFRESH)
                times.add(timeToMilliseconds)
                refresh()
                resetDialogPicker()
                dialog?.dismiss()
            }
            
        }
        
    }
    
    private fun refresh() {
        setHeaderImage()
        getStats()
    }
    
    private fun resetDialogPicker() {
        numberPickers.forEach { it.value = 0 }
    }
    
    private fun launchDialog() {
        dialog?.show()
    }
    
    private fun loadImageToHeader() {
        subcategory.imageURL?.takeIf {
            it.isNotBlank()
        }?.let {
            Glide.with(this).load(it)
                .centerCrop() // scale image to fill the entire ImageView
                .circleCrop().into(binding.activityGameDetailsImageHeader)
            return
        }
        
        Glide.with(this).load(R.drawable.ill_image_upload_amico)
            .centerInside()
            .circleCrop().into(binding.activityGameDetailsImageHeader)
    }
    
    private fun setPlaceHolder() {
        val stats = binding.activityGameDetailsTextYourStats
        val ranking = binding.activityGameDetailsTextRanking
        
        // TODO("Use cache with sharedPreferences")
        val resStats = resources.getString(R.string.game_details_text_stats, getString(currentTimeSort.resStringId))
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
    
    private fun getStats() {
        statistics.clear()
        times.getBestToMilliseconds().let {
            bestInMilliseconds = it
            statistics.add(Pair(StatisticType.BEST, it.toFormatTime()))
        }
        times.getAverageToMilliseconds().let {
            averageInMilliseconds = it
            statistics.add(Pair(StatisticType.AVERAGE, viewModel?.getToggleFromSharedPreferences(SettingType.DISPLAY_MILLISECONDS)?.let { toggle ->
                if (!toggle) it.toFormatTimeWithoutMilliseconds() else it.toFormatTime()
            } ?: it.toFormatTime()))
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
    
    /**
     * Reduce size image based on content shape size for create a border
     */
    private fun setHeaderImage() {
        val image = binding.activityGameDetailsImageHeader
        val imageContent = binding.activityGameDetailsContentImageHeader
        
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
        binding.activityGameDetailsTextNameLevel.text = resources.getString(R.string.game_details_header_level_name, subcategory.name)
        binding.activityGameDetailsTextCategory.text = resources.getString(R.string.game_details_header_level_name, categoryName)
        binding.activityGameDetailsTextTime.text = resources.getString(R.string.game_details_header_level_name, bestOnAllRunsInMilliseconds.toFormatTime())
    }
    
    override fun setUp() {
        viewModel = (vm as SubcategoryViewModel?)?.also {
            it.build()
            it.buildSharedPreferences(this, Constants.KEY_SHARED_PREFERENCES)
        }
        
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
        getTimes()
    }
    
    
    private fun getTimes() {
        times.clear()
        statisticAdapter?.notifyDataSetChanged()
        
        val subCategoryId = subcategory.id
        
        with(viewModel ?: return) {
            getSubCategoryTimes(categoryId, subCategoryId, currentTimeSort)?.observe(this@SubCategoryActivity, { resultTimes ->
                if (resultTimes.state != ResultLiveDataType.SUCCESS) return@observe
                
                resultTimes.result!!.forEach { time ->
                    times.add(time.time)
                }
                
                refresh()
                
                getBestOnAllRuns(subCategoryId)
            })
        }
    }
    
    private fun SubcategoryViewModel.getBestOnAllRuns(subCategoryId: String) {
        getBestOnAllRuns(categoryId, subCategoryId)?.observe(this@SubCategoryActivity, { resultBest ->
            if (resultBest.state != ResultLiveDataType.SUCCESS) return@observe
            bestOnAllRunsInMilliseconds = resultBest.result!!.timeInMilliseconds
            refreshBest()
        })
    }
    
    private fun refreshBest() {
        getHeader()
    }
    
    override fun onClick() {
        
        binding.activityGameDetailsTextYourStats.setOnClickListener {
            onClickTimeMenu(it)
        }
        
        binding.activityGameDetailsButtonAddYourStats.setOnClickListener {
            launchDialog()
        }
        
        binding.activityGameDetailsButtonListYourStats.setOnClickListener {
            val intent = Intent(this, TimesActivity::class.java)
            
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            intent.putExtra(EXTRA_SUBCATEGORY_ID, subcategory.id)
            
            resultLauncher.launch(intent)
        }
    }
    
    override fun onPersonnelBestTime(): Long {
        return bestInMilliseconds
    }
    
    override fun onBestTime(): Long {
        return bestInMilliseconds
    }
}