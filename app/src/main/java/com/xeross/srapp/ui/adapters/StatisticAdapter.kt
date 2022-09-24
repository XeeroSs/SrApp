package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.data.models.types.StatisticType
import com.xeross.srapp.databinding.StatsCellBinding

class StatisticAdapter(private val context: Context, private val data: ArrayList<Pair<StatisticType, String>>) : RecyclerView.Adapter<StatisticAdapter.ViewHolder>() {
    
    class ViewHolder(binding: StatsCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val result: TextView = binding.statsCellResult
        val category: TextView = binding.statsCellCategory
    }
    
    override fun getItemCount() = data.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = data.getOrNull(position)?.first ?: return
        val value = data.getOrNull(position)?.second ?: return
        updateItem(holder, type, value)
    }
    
    private fun updateItem(holder: ViewHolder, type: StatisticType, value: String) {
        holder.result.text = value
        holder.category.text = context.getString(type.resStringId)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(StatsCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
}