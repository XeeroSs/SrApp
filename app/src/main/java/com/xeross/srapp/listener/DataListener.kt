package com.xeross.srapp.listener

interface DataListener<T> {
    fun get(): T
}