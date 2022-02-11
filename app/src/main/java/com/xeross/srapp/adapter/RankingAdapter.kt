package com.xeross.srapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.model.Ranking
import kotlinx.android.synthetic.main.game_cell.view.*
import kotlinx.android.synthetic.main.ranking_cell.view.*

class RankingAdapter(context: Context, objectList: ArrayList<Ranking>) :
    BaseAdapter<RankingAdapter.ViewHolder, Ranking>(context, objectList, null) {
    
    private val glide = Glide.with(context)
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menu: ImageButton = itemView.ranking_cell_button_menu
        val difference: TextView = itemView.ranking_cell_difference
        val profileImage: ImageView = itemView.ranking_cell_profile_image
        val name: TextView = itemView.ranking_cell_name
        val time: TextView = itemView.ranking_cell_time
        val position: TextView = itemView.ranking_cell_position
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: Ranking) {
    }
    
    override fun updateItem(holder: ViewHolder, dObject: Ranking) {
        holder.difference.text = "Diff Best: -3s / PB: +6s"
        holder.name.text = dObject.name
        holder.time.text = "Temps: " +dObject.time
        holder.position.text = dObject.position.toString() + "."
        //   holder.position.text = context.getString(R.string.game_position, dObject.position)
        glide.load(dObject.profileImage).into(holder.profileImage)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.ranking_cell, parent, false))
    
    
}