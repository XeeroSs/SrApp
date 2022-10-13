package com.xeross.srapp.listener

interface AsyncRecyclerListener<VH, O> {
    fun execute(holder: VH, dObject: O)
}