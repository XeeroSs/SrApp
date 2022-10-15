package com.xeross.srapp.ui.times

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xeross.srapp.R
import com.xeross.srapp.base.activity.BaseActivity
import com.xeross.srapp.components.NumberPickerBuilder
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.databinding.ActivityTimesBinding
import com.xeross.srapp.databinding.DialogAddTimeBinding
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.TimeAdapter
import com.xeross.srapp.ui.category.subcategories.SubcategoriesActivity
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toHourFromTimeInMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toMillisecondFromTimeInMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toMinuteFromTimeInMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toSecondFromTimeInMilliseconds
import java.util.*
import java.util.stream.Collectors

class TimesActivity : BaseActivity<ActivityTimesBinding>(), TimeAdapter.Listener, ClickListener<Time> {
    
    private val times = ArrayList<Time>()
    private var adapter: TimeAdapter? = null
    
    private lateinit var subcategoryId: String
    private lateinit var categoryId: String
    
    // Dialogs
    private var dialogView: DialogAddTimeBinding? = null
    private var dialog: AlertDialog? = null
    
    private var best: Long = 0
    private var average: Long = 0
    private var worst: Long = 0
    private var isDeleting: Boolean = false
    
    private var hoursPicker: NumberPicker? = null
    private var minutesPicker: NumberPicker? = null
    private var secondsPicker: NumberPicker? = null
    private var millisecondsPicker: NumberPicker? = null
    
    private val numberPickers = ArrayList<NumberPicker>()
    
    override fun getViewModelClass() = TimesViewModel::class.java
    private var viewModel: TimesViewModel? = null
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityTimesBinding.inflate(layoutInflater)
    }
    
    override fun setUp() {
        viewModel = vm as TimesViewModel?
        viewModel?.build()
        
        subcategoryId = intent.getStringExtra(Constants.EXTRA_SUBCATEGORY_ID) ?: run {
            finish()
            return
        }
        
        categoryId = intent.getStringExtra(Constants.EXTRA_CATEGORY_ID) ?: run {
            finish()
            return
        }
        
        getTimes()
    }
    
    override fun ui() {
        
        buildHeader(binding.header.headerToolbar, binding.header.headerTitle, R.string.times, 35f)
        
        setStatusBarTransparent()
        buildBottomNavigationMenu(binding.menu.bottomNavigationMenu)
        setUpDialogs()
        
        adapter = TimeAdapter(this, times, Date(), this, this).also { a ->
            binding.listTimes.let {
                val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
        // Method View::post allows to call the Thread for UI
        binding.listTimes.post {
            
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = binding.listTimes.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = binding.menu.bottomNavigationMenu.measuredHeight
        }
    }
    
    private fun showTrashButton(toggle: Boolean) {
        val transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(binding.buttonTrash)
        
        TransitionManager.beginDelayedTransition(binding.parentView, transition)
        binding.buttonTrash.visibility = if (toggle) View.VISIBLE else View.GONE
    }
    
    private fun getTimes() {
        times.clear()
        viewModel?.getTimes(categoryId, subcategoryId)?.observe(this) {
            if (it == null) return@observe
            times.addAll(it)
            getListenerTime(it)
            adapter?.notifyDataSetChanged()
        }
    }
    
    private fun getListenerTime(times: ArrayList<Time>) {
        times.stream().map(Time::time).collect(Collectors.toList()).let { time ->
            best = time.getBestToMilliseconds()
            average = time.getAverageToMilliseconds()
            worst = time.getWorstToMilliseconds()
        }
    }
    
    override fun toggle(toggle: Boolean) {
        showTrashButton(toggle)
    }
    
    override fun average() = average
    override fun worst() = worst
    override fun best() = best
    override fun isDeleting() = isDeleting
    
    override fun onClick(o: Time) {
        launchDialog(o)
    }
    
    override fun onLongClick(o: Time) {
    }
    
    private fun NumberPicker.add(): NumberPicker {
        numberPickers.add(this)
        return this
    }
    
    private fun setUpDialogs() {
        dialogView = DialogAddTimeBinding.inflate(LayoutInflater.from(this), null, false).also {
            
            hoursPicker = NumberPickerBuilder(it.hourPicker).max(999).min().build().add()
            minutesPicker = NumberPickerBuilder(it.minutePicker).max(59).min().format { d -> if (d < 10) return@format "0$d" else return@format d.toString() }.build().add()
            secondsPicker = NumberPickerBuilder(it.secondPicker).max(59).min().format { d -> if (d < 10) return@format "0$d" else return@format d.toString() }.build().add()
            millisecondsPicker = NumberPickerBuilder(it.millisecondPicker).max(999).min().format { d -> if (d < 10) return@format "00$d" else if (d < 100) return@format "0$d" else return@format d.toString() }.build().add()
            
            it.dismissButton?.setOnClickListener { _ ->
                resetDialogPicker()
                dialog?.dismiss()
            }
            
            dialog = MaterialAlertDialogBuilder(this, R.style.WrapEverythingDialog).setBackground(ColorDrawable(Color.TRANSPARENT)).setCancelable(true).setView(it.root).create()
        }
    }
    
    private fun toggleNumberPickers(toggle: Boolean) {
        numberPickers.forEach { it.isEnabled = toggle }
    }
    
    private fun updateTime(time: Time) {
        dialogView?.submitButton?.isEnabled = false
        getTimeFromDialog(hoursPicker!!.value, minutesPicker!!.value, secondsPicker!!.value, millisecondsPicker!!.value, time)?.observe(this, { timeInMilliseconds ->
            dialogView?.submitButton?.isEnabled = true
            if (timeInMilliseconds == null) return@observe
            adapter?.let { a ->
                times.find { it.timeId == time.timeId }?.time = timeInMilliseconds
                refresh(a)
            }
            resetDialogPicker()
            dialog?.dismiss()
        })
    }
    
    private fun resetDialogPicker() {
        numberPickers.forEach { it.value = 0 }
    }
    
    private fun launchDialog(time: Time) {
        
        dialogView?.submitButton?.setOnClickListener { _ ->
            // The value inputted by the user is not updated if the user is still in the text input of a NumberPicker (In the case where the user himself inputs the value of the NumberPicker with his keyboard).
            // However, disabling it before getting its value allows it to be updated.
            toggleNumberPickers(false)
            updateTime(time)
            toggleNumberPickers(true)
        }
        
        with(time.time) {
            hoursPicker?.value = toHourFromTimeInMilliseconds().toInt()
            minutesPicker?.value = toMinuteFromTimeInMilliseconds().toInt()
            secondsPicker?.value = toSecondFromTimeInMilliseconds().toInt()
            millisecondsPicker?.value = toMillisecondFromTimeInMilliseconds().toInt()
        }
        
        dialog?.show()
    }
    
    private fun getTimeFromDialog(hours: Int, minutes: Int, seconds: Int, milliseconds: Int, time: Time): LiveData<Long?>? {
        val timeToMilliseconds = (milliseconds + 0L) + (seconds * 1000L) + (minutes * 60 * 1000L) + (hours * 60L * 60L * 1000L)
        
        return viewModel?.updateTime(categoryId, subcategoryId, timeToMilliseconds, time)
    }
    
    override fun onClick() {
        binding.buttonTrash.setOnClickListener { _ ->
            adapter?.let { a ->
                a.getToggles().takeUnless { it.isEmpty() }?.let { toggles ->
                    viewModel?.let { vm ->
                        isDeleting = true
                        binding.buttonTrash.isEnabled = false
                        vm.deleteTimes(this, categoryId, subcategoryId, toggles, times).observe(this, { isOk ->
                            if (isOk) {
                                toggles.forEach(times::remove)
                                a.getToggles().removeAll(toggles)
                                refresh(a)
                            }
                            
                            isDeleting = false
                            binding.buttonTrash.isEnabled = true
                            showTrashButton(false)
                        })
                    }
                }
            }
        }
    }
    
    private fun refresh(a: TimeAdapter) {
        setResult(SubcategoriesActivity.RC_REFRESH)
        getListenerTime(times)
        a.notifyDataSetChanged()
    }
}