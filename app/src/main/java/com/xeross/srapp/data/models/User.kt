package com.xeross.srapp.data.models

import com.google.firebase.Timestamp

data class User(val id: String = "", var pseudo: String = "???", var profileImageURL: String? = null, val createdAt: Timestamp = Timestamp.now(),
                var totalBest: Int = 0, var totalTimes: Int = 0, var lastBestAt: Timestamp = Timestamp.now())