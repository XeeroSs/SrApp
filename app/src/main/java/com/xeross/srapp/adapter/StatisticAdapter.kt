package com.xeross.srapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.model.Statistic
import kotlinx.android.synthetic.main.stats_cell.view.*

class StatisticAdapter(context: Context, objectList: ArrayList<Statistic>) :
    BaseAdapter<StatisticAdapter.ViewHolder, Statistic>(context, objectList, null) {
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val result: TextView = itemView.stats_cell_result
        val category: TextView = itemView.stats_cell_category
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: Statistic) {
    }
    
    override fun updateItem(holder: ViewHolder, dObject: Statistic) {
        holder.result.text = dObject.result
        holder.category.text = dObject.category
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.stats_cell, parent, false))
    
    
}