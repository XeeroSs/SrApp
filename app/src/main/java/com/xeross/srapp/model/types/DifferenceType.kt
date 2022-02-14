package com.xeross.srapp.model.types

import com.xeross.srapp.R

enum class DifferenceType(val resColor: Int?, val prefix: String) {
    
    EQUAL(null, "-"),
    POSITIVE(R.color.negative, "+"),
    NEGATIVE(R.color.positive, "")
    
}