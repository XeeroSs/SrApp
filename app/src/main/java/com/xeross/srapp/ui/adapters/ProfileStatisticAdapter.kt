package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.ProfileStatistic
import com.xeross.srapp.databinding.CellProfileStatisticBinding

class ProfileStatisticAdapter(context: Context, objectList: ArrayList<ProfileStatistic>) :
    BaseAdapter<ProfileStatisticAdapter.ViewHolder, ProfileStatistic>(context, objectList, null) {
    
    class ViewHolder(binding: CellProfileStatisticBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.textName
        val value: TextView = binding.textValue
        val icon: ImageView = binding.imageIcon
        val item: MaterialCardView = binding.shapeRectangle
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
        ViewHolder(CellProfileStatisticBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
}