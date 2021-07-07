package com.xeross.srapp.controller.celeste

import androidx.lifecycle.ViewModel

class CelesteViewModel() : ViewModel() {

    private var nSheets = ""

    fun build(nSheets: String) {
        this.nSheets = nSheets
    }

}