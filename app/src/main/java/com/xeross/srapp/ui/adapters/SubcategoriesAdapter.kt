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
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import kotlinx.android.synthetic.main.cell_subcategory.*
import kotlinx.android.synthetic.main.cell_subcategory.view.*
import java.util.*

class SubcategoriesAdapter(context: Context, objectList: ArrayList<SubCategory>, clickListener: ClickListener<SubCategory>) :
    BaseAdapter<SubcategoriesAdapter.ViewHolder, SubCategory>(context, objectList, clickListener) {
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: MaterialCardView = itemView.item_subcategory
        val name: TextView = itemView.text_name_subcategory
        val time: TextView = itemView.text_time_subcategory
        // val graphic: ChartProgressBar = itemView.graphic_subcategories
    }
    
    // TODO("Set up graphic")
    private fun setUpGraphic(holder: ViewHolder) {
/*        val graphics = holder.graphic
        val color = ContextCompat.getColor(context, R.color.pink_gradient_light_variant)
        val points = arrayListOf<BarData>()
        repeat(10) {
            points.add(BarData("", 5.0f))
        }
        
        graphics.setDataList(points)
        graphics.build()*/
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
        holder.time.text = dObject.timeInSeconds.toFormatTime()
        holder.name.text = dObject.name
        
        setUpGraphic(holder)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_subcategory, parent, false))
    
    
}