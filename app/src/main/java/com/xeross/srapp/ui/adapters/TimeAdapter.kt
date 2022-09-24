package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.Time
import com.xeross.srapp.databinding.TimeCellBinding
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.utils.extensions.TimeExtensions.timeAgoToString
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import java.util.*

class TimeAdapter(context: Context, objectList: ArrayList<Time>, private val current: Date, clickListener: ClickListener<Time>, private val listener: Listener) :
    BaseAdapter<TimeAdapter.ViewHolder, Time>(context, objectList, clickListener) {
    
    private val toggles = ArrayList<Time>()
    
    interface Listener {
        fun average(): Long
        fun best(): Long
        fun worst(): Long
        
        fun isDeleting(): Boolean
        fun toggle(toggle: Boolean)
    }
    
    class ViewHolder(binding: TimeCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val item: RelativeLayout = binding.item
        val timeAgo: TextView = binding.textDaysAgo
        val checkBox: MaterialCheckBox = binding.checkBox
        val cardview: MaterialCardView = binding.cardviewTime
        val time: TextView = binding.textTime
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: Time) {
        holder.item.setOnClickListener {
            clickListener?.onClick(dObject)
        }
    }
    
    override fun updateItem(holder: ViewHolder, dObject: Time) {
        holder.timeAgo.text = (dObject.createdAt.seconds * 1000).timeAgoToString(context, current)
        val time = dObject.time
        val resColor = when {
            time == listener.best() -> R.color.gold
            time == listener.worst() -> R.color.worst
            time > listener.average() -> R.color.negative
            else -> R.color.positive
        }
        val color = ContextCompat.getColor(context, resColor)
        holder.time.apply {
            text = time.toFormatTime()
            setTextColor(color)
        }
        holder.cardview.strokeColor = color
        
        holder.checkBox.setOnCheckedChangeListener { _, toggle ->
            if (listener.isDeleting()) {
                holder.checkBox.isChecked = !toggle
                return@setOnCheckedChangeListener
            }
            if (toggle) toggles.add(dObject) else toggles.remove(dObject)
            listener.toggle(toggles.isNotEmpty())
        }
    }
    
    fun getToggles() = toggles
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(TimeCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
}