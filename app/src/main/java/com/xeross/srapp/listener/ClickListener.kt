package com.xeross.srapp.listener

interface ClickListener<T> {
    fun onClick(o: T)
    fun onLongClick(o: T)
}