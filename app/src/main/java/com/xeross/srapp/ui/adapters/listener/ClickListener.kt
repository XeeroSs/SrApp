package com.xeross.srapp.ui.adapters.listener

interface ClickListener<T> {
    fun onClick(o: T)
    fun onLongClick(o: T)
}