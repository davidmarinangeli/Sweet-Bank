package com.davidm.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class HorizontalLayoutManager(context: Context?) : LinearLayoutManager(context) {
    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        isScrollEnabled = flag
    }

    override fun canScrollHorizontally(): Boolean {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically()
    }
}