package com.rolemodel.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getScrollPosition(): Int {
    return if (layoutManager is GridLayoutManager)
        (layoutManager
                as? GridLayoutManager)
                ?.findFirstVisibleItemPosition() ?: 0
    else
        (layoutManager
                as? LinearLayoutManager)
                ?.findFirstVisibleItemPosition() ?: 0
}
