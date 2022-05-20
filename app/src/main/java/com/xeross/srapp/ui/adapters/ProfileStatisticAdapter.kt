package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.ProfileStatistic
import kotlinx.android.synthetic.main.cell_profile_statistic.view.*

class ProfileStatisticAdapter(context: Context, objectList: ArrayList<ProfileStatistic>) :
    BaseAdapter<ProfileStatisticAdapter.ViewHolder, ProfileStatistic>(context, objectList, null) {
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.text_name
        val value: TextView = itemView.text_value
        val icon: ImageView = itemView.image_icon
        val item: MaterialCardView = itemView.shape_rectangle
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: ProfileStatistic) {
    }
    
    override fun updateItem(holder: ViewHolder, dObject: ProfileStatistic) {
        with(holder) {
            name.text = context.resources.getString(dObject.resNameId)
            value.text = dObject.value
            
            icon.setImageResource(dObject.resIconId)
            
            item.setCardBackgroundColor(ContextCompat.getColor(context, dObject.resColorId))
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_profile_statistic, parent, false))
    
    
}