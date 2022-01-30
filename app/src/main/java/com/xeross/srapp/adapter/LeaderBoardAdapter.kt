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
import com.xeross.srapp.model.LeaderBoard
import kotlinx.android.synthetic.main.leader_board_cell.view.*

class LeaderBoardAdapter(context: Context, objectList: ArrayList<LeaderBoard>, clickListener: ClickListener<LeaderBoard>) :
        BaseAdapter<LeaderBoardAdapter.ViewHolder, LeaderBoard>(context, objectList, clickListener) {

  //  private val glide = Glide.with(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val difference_time = itemView.leader_board_cell_difference_time
        val place = itemView.leader_board_cell_place
        val speedrunnerName = itemView.leader_board_cell_speedrunner_name
        val time = itemView.leader_board_cell_time
    }


    override fun onClick(holder: ViewHolder, dObject: LeaderBoard) {
    }

    override fun updateItem(holder: ViewHolder, dObject: LeaderBoard) {
        holder.difference_time.text = dObject.difference_time
        holder.place.text = dObject.place
        holder.speedrunnerName.text = dObject.name
        holder.time.text = dObject.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.leader_board_cell, parent, false))


}