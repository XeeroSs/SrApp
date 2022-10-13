package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.databinding.CategoryCellBinding
import com.xeross.srapp.listener.AsyncRecyclerListener
import com.xeross.srapp.listener.ClickListener

class CategoryAdapter(context: Context, objectList: ArrayList<Category>, clickListener: ClickListener<Category>, val asyncRecyclerListener: AsyncRecyclerListener<ViewHolder, Category>) :
    BaseAdapter<CategoryAdapter.ViewHolder, Category>(context, objectList, clickListener) {
    
    private val glide = Glide.with(context)
    
    inner class ViewHolder(binding: CategoryCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.categoryCellImage
        val info: TextView = binding.categoryCellInfo
        val category: TextView = binding.categoryCellCategory
        val item: LinearLayout = binding.categoryCellItem
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
        holder.info.text = "9 catégories • #6"
        holder.category.text = dObject.name
        
        dObject.imageURL?.takeIf { it.isNotBlank() }?.let { url ->
            
            asyncRecyclerListener.execute(holder, dObject)
            
        } ?: run {
            holder.image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            glide.load(R.drawable.ill_gaming_amico).into(holder.image)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CategoryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    
}