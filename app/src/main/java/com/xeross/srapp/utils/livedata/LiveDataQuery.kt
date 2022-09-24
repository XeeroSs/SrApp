package com.xeross.srapp.utils.livedata

data class LiveDataQuery<T>(val state: ResultLiveDataType, val resMessageId: Int, val result: T?)