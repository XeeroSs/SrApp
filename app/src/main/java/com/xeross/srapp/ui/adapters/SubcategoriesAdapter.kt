package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.components.ui.GraphicBar
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import kotlinx.android.synthetic.main.cell_subcategory.*
import kotlinx.android.synthetic.main.cell_subcategory.view.*
import java.util.*

class SubcategoriesAdapter(context: Context, objectList: ArrayList<SubCategory>, clickListener: ClickListener<SubCategory>, private val graGraphicListener: GraphicListener) :
    BaseAdapter<SubcategoriesAdapter.ViewHolder, SubCategory>(context, objectList, clickListener) {
    
    interface GraphicListener {
        fun notifyGraphicDataChanged(graphicBar: GraphicBar, subCategory: SubCategory)
    }
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: MaterialCardView = itemView.item_subcategory
        val name: TextView = itemView.text_name_subcategory
        val time: TextView = itemView.text_time_subcategory
        val graphic: GraphicBar = itemView.graphic_subcategories
    }
    
    override fun onClick(holder: ViewHolder, dObject: SubCategory) {
        holder.item.setOnClickListener {
            clickListener?.onClick(dObject)
        }
        holder.item.setOnLongClickListener {
            clickListener?.onLongClick(dObject)
            return@setOnLongClickListener true
        }
    }
    
    // TODO("do graphic")
    override fun updateItem(holder: ViewHolder, dObject: SubCategory) {
        holder.time.text = dObject.timeInMilliseconds.toFormatTime()
        holder.name.text = dObject.name
        
        holder.graphic.post { graGraphicListener.notifyGraphicDataChanged(holder.graphic, dObject) }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_subcategory, parent, false))
    
    
}