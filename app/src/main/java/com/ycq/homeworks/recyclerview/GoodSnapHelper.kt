package com.ycq.homeworks.recyclerview

import android.view.View
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import com.ycq.homeworks.util.dp
import kotlin.math.abs

/**
 * align left
 * align center
 * max pages
 * 注意：没有处理 Vertical 的情况
 */
class GoodSnapHelper(val interpolator: Interpolator?) : LinearSnapHelper() {
    private var mGravityScroller: Scroller? = null


    @IntDef(ALIGN_CENTER, ALIGN_LEFT)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Align{}
    companion object {
        const val ALIGN_CENTER = 0
        const val ALIGN_LEFT = 1
    }

    @Align
    var align = ALIGN_CENTER
    private var mHorizontalHelper: OrientationHelper? = null


    /**
     * 算出 targetView 到需要对齐的位置的距离，然后做动画
     */
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        return if (align == ALIGN_CENTER) {
            super.calculateDistanceToFinalSnap(
                layoutManager,
                targetView
            )
        } else {
            val ints = super.calculateDistanceToFinalSnap(layoutManager, targetView)
            val helper = getHorizontalHelper(layoutManager)
            val target = helper.startAfterPadding
            val current = helper.getDecoratedStart(targetView)
            ints?.set(0, current - target)
            ints
        }
    }


    /**
     *
     * ALIGN_CENTER 找到中间那个 View
     * ALIGN_LEFT 找到第一个没有超过左边界一半的 View
     */
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return if (align == ALIGN_CENTER) {
            super.findSnapView(layoutManager)
        } else {
            findAlignLeftSnapView(layoutManager)
        }
    }

    /**
     * ALIGN_LEFT 找到第一个没有超过左边界一半的 View
     */
    private fun findAlignLeftSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager == null) return null
        return if (layoutManager is LinearLayoutManager) {
            //找出第一个可见的ItemView的位置
            val firstChildPosition = layoutManager.findFirstVisibleItemPosition()
            if (firstChildPosition == RecyclerView.NO_POSITION) {
                return null
            }
            //找到最后一个完全显示的 ItemView，如果该ItemView是列表中的最后一个
            //就说明列表已经滑动最后了，这时候就不应该根据第一个 ItemView 来对齐了
            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null
            }
            val firstChildView = layoutManager.findViewByPosition(firstChildPosition) ?: return null
            //如果第一个ItemView被遮住的长度没有超过一半，就取该ItemView作为snapView
            //超过一半，就把下一个ItemView作为snapView
            val helper = getHorizontalHelper(layoutManager)
            if (helper.getDecoratedEnd(firstChildView) >= helper.getDecoratedMeasurement(
                    firstChildView
                ) / 2
                && helper.getDecoratedEnd(firstChildView) > 0
            ) {
                firstChildView
            } else {
                layoutManager.findViewByPosition(firstChildPosition + 1)
            }
        } else {
            null
        }
    }

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

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        // 这里没用到这个逻辑
        val vectorForEnd = layoutManager.computeScrollVectorForPosition(itemCount - 1)
            ?: // cannot get a vector for the given position.
            return RecyclerView.NO_POSITION

        var hDeltaJump: Int
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = getNextPosition(velocityX)
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump
            }
        } else {
            hDeltaJump = 0
        }


        val deltaJump = hDeltaJump
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION
        }

        var targetPos = currentPosition + deltaJump
        if (targetPos < 0) {
            targetPos = 0
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1
        }
        return targetPos
    }

    // 0 - 2000          0
    // 2000 - 6000       1
    // 6000 - 12000      2
    private fun getNextPosition(velocityX: Int): Int {
        val dpV = abs(velocityX / 1.dp)
        return if (0 <= dpV && dpV < 1500 / 1.dp) {
            0
        } else if (1500 / 1.dp <= dpV && dpV < 8000 / 1.dp) {
            1
        } else if (8000 / 1.dp <= dpV && dpV < 16000 / 1.dp) {
            2
        } else {
            3
        }
    }

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        if (mHorizontalHelper == null || mHorizontalHelper!!.layoutManager !== layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }
}
