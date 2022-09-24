package com.xeross.srapp.data.models

import com.google.firebase.Timestamp

data class SubCategory(val id: String = "", var position: Int = 0, var name: String = "", var imageURL: String? = null, var timeInMilliseconds: Long = 0, var createdAt: Timestamp = Timestamp.now(),
                        var colorHex:String = "FC5185")