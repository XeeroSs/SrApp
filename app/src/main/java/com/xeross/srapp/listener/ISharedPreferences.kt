package com.xeross.srapp.listener

interface ISharedPreferences<T> {
    
    fun getKey(): String
    fun getDefaultValue(): T
    
}