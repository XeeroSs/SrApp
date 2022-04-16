package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import kotlinx.android.synthetic.main.cell_subcategory.view.*

class SubcategoriesAdapter(context: Context, objectList: ArrayList<SubCategory>, clickListener: ClickListener<SubCategory>) :
    BaseAdapter<SubcategoriesAdapter.ViewHolder, SubCategory>(context, objectList, clickListener) {
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: CardView = itemView.item_subcategory
        val name: TextView = itemView.text_name_subcategory
        val time: TextView = itemView.text_time_subcategory
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: SubCategory) {
        val item = holder.item
        item.setOnClickListener {
            clickListener?.onClick(dObject)
        }
        item.setOnLongClickListener {
            clickListener?.onLongClick(dObject)
            return@setOnLongClickListener true
        }
    }
    
    // TODO("do graphic")
    override fun updateItem(holder: ViewHolder, dObject: SubCategory) {
        holder.time.text = dObject.timeInSeconds.toFormatTime()
        holder.name.text = dObject.name
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_subcategory, parent, false))
    
    
}