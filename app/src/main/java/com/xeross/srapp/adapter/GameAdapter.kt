package com.xeross.srapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import kotlinx.android.synthetic.main.game_cell.view.*

class GameAdapter(context: Context, objectList: ArrayList<Game>, clickListener: ClickListener<Game>) :
        BaseAdapter<GameAdapter.ViewHolder, Game>(context, objectList, clickListener) {

    private val glide = Glide.with(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.game_cell_title
        val position = itemView.game_cell_position
        val img = itemView.game_cell_img
        val item = itemView.game_cell_item
    }


    override fun onClick(holder: ViewHolder, dObject: Game) {
        holder.item.setOnClickListener {
            clickListener.onClick(dObject)
        }
    }

    override fun updateItem(holder: ViewHolder, dObject: Game) {
        holder.title.text = dObject.name
        holder.position.text = context.getString(R.string.game_position, dObject.position)
        glide.load(dObject.img).into(holder.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.game_cell, parent, false))


}