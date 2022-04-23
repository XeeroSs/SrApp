package com.xeross.srapp.data.models

import com.google.firebase.Timestamp

data class Time(var timeId: String = "", var time: Long = 0, var createdAt: Timestamp = Timestamp.now())
