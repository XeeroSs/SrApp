package com.xeross.srapp.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.listener.ClickListener

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T>(protected val context: Context, private val objectList: ArrayList<T>,
                                                            protected val clickListener: ClickListener<T>) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = objectList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val dObject = objectList[position]
        updateItem(holder, dObject)
        onClick(holder, dObject)
    }

    abstract fun onClick(holder: VH, dObject: T)

    abstract fun updateItem(holder: VH, dObject: T)
}