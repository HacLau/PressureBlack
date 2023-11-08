package com.testbird.pressureblack.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressureblack.appContext
import com.testbird.pressureblack.contacts.dp2px

class ItemTopDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.top = it
        }
    }
}

class ItemBottomDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.bottom = it
        }
    }
}

class ItemLRDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.left = it
            outRect.right = it
        }
    }
}
class ItemTLRDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.top = it
            outRect.left = it
            outRect.right = it
        }
    }
}

class ItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.left = it
            outRect.right = it
            outRect.top = it
            outRect.bottom = it
        }
    }
}