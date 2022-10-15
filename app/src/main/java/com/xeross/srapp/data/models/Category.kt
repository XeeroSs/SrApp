package com.xeross.srapp.data.models

import com.google.firebase.Timestamp

data class Category(val id: String = "", var name: String = "", var imageURL: String? = null, var lastUpdatedAt: Timestamp = Timestamp.now(),
                    var createdAt: Timestamp = Timestamp.now())
