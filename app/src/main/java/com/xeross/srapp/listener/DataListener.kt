package com.xeross.srapp.listener

interface DataListener<T> {
    fun notifyDataChanged(data: T)
}