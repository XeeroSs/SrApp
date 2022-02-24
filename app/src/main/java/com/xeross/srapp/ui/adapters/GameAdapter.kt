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
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.data.models.Game
import kotlinx.android.synthetic.main.game_cell.view.*

class GameAdapter(context: Context, objectList: ArrayList<Game>, clickListener: ClickListener<Game>) :
    BaseAdapter<GameAdapter.ViewHolder, Game>(context, objectList, clickListener) {
    
    private val glide = Glide.with(context)
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.game_cell_image
        val info: TextView = itemView.game_cell_info
        val category: TextView = itemView.game_cell_category
        val item: LinearLayout = itemView.game_cell_item
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: Game) {
        holder.item.setOnClickListener {
            holder.image.performClick()
            clickListener?.onClick(dObject)
        }
    
        holder.item.setOnLongClickListener {
            clickListener?.onLongClick(dObject)
            return@setOnLongClickListener true
        }
    }
    
    override fun updateItem(holder: ViewHolder, dObject: Game) {
        holder.info.text = "9 catégories • #" + dObject.position
        holder.category.text = dObject.name
        //   holder.position.text = context.getString(R.string.game_position, dObject.position)
        glide.load(dObject.img).into(holder.image)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.game_cell, parent, false))
    
    
}