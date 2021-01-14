package com.ycq.homeworks.recyclerview

import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * align left
 * align center
 * max pages
 */
class GoodSnapHelper(val interpolator : Interpolator?) : LinearSnapHelper() {
    private var mGravityScroller: Scroller? = null

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        if (recyclerView != null) {
            mGravityScroller = Scroller(
                recyclerView.context,
                interpolator
            )
        }
    }

    override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray {
        val outDist = IntArray(2)
        mGravityScroller!!.fling(
            0,
            0,
            velocityX,
            velocityY,
            Int.MIN_VALUE,
            Int.MAX_VALUE,
            Int.MIN_VALUE,
            Int.MAX_VALUE
        )
        outDist[0] = mGravityScroller!!.finalX
        outDist[1] = mGravityScroller!!.finalY
        return outDist
    }
}