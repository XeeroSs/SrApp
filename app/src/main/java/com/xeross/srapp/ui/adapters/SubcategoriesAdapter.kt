package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.components.ui.GraphicBar
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.databinding.CellSubcategoryBinding
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import com.xeross.srapp.utils.extensions.UIExtensions

class SubcategoriesAdapter(context: Context, objectList: ArrayList<SubCategory>, clickListener: ClickListener<SubCategory>, private val graGraphicListener: GraphicListener) :
    BaseAdapter<SubcategoriesAdapter.ViewHolder, SubCategory>(context, objectList, clickListener) {
    
    interface GraphicListener {
        fun notifyGraphicDataChanged(graphicBar: GraphicBar, subCategory: SubCategory)
    }
    
    class ViewHolder(binding: CellSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val item: MaterialCardView = binding.itemSubcategory
        val content: LinearLayout = binding.content
        val name: TextView = binding.textNameSubcategory
        val time: TextView = binding.textTimeSubcategory
        val graphic: GraphicBar = binding.graphicSubcategories
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
    
    
    override fun updateItem(holder: ViewHolder, dObject: SubCategory) {
        holder.time.text = dObject.timeInMilliseconds.toFormatTime()
        holder.name.text = dObject.name
        
        holder.content.background = UIExtensions.getGradientWithSingleColor(context, R.color.pink_gradient)
        
        holder.graphic.post {
            graGraphicListener.notifyGraphicDataChanged(holder.graphic, dObject)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CellSubcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    
}