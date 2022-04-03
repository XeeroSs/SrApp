package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.ui.adapters.listener.ClickListener
import kotlinx.android.synthetic.main.category_cell.view.*

class CategoryAdapter(context: Context, objectList: ArrayList<Category>, clickListener: ClickListener<Category>) :
    BaseAdapter<CategoryAdapter.ViewHolder, Category>(context, objectList, clickListener) {
    
    private val glide = Glide.with(context)
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.category_cell_image
        val info: TextView = itemView.category_cell_info
        val category: TextView = itemView.category_cell_category
        val item: LinearLayout = itemView.category_cell_item
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: Category) {
        holder.item.setOnClickListener {
            holder.image.performClick()
            clickListener?.onClick(dObject)
        }
        
        holder.item.setOnLongClickListener {
            clickListener?.onLongClick(dObject)
            return@setOnLongClickListener true
        }
    }
    
    override fun updateItem(holder: ViewHolder, dObject: Category) {
        holder.info.text = "9 catégories • #$dObject.id"
        holder.category.text = dObject.name
        
        dObject.imageURL?.takeIf { it.isNotBlank() }?.let { url ->
            glide.load(url).into(holder.image)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_cell, parent, false))
    
    
}