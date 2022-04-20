package com.xeross.srapp.data.models

import java.sql.Timestamp

data class SubCategory(val id: String = "", var position: Int = 0, var name: String = "", var imageURL: String? = null, var timeInSeconds: Long = 0, var createdAt: Timestamp? = null)