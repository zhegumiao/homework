package com.ycq.homeworks.draw.class22ScalableImageView

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar

/**
 *
 */
class ScalableImageView constructor(context: Context, attrs: AttributeSet?) :
        View(context, attrs) {
    private val bitmap = getAvatar(50.dpInt)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmapWidth = bitmap.width
    private val bitmapHeight = bitmap.height

    private var bitmapLeft = 0
    private var bitmapTop = 0
    private var centerX = 0
    private var centerY = 0

    /**
     * 是否对齐上下
     * true 对齐上下
     * false 对齐左右
     */
    private var isBigScale = false
    private var bigScale = 1f
    private var smallScale = 1f
    private var minX = 0f
    private var minY = 0f

    private val dragOver = 100.dpInt

    var scale = 1f
        set(value) {
            field = value
            invalidate()
        }

    private val animator = ObjectAnimator.ofFloat(this, "scale", smallScale, bigScale).apply {
        duration = 800
    }

    private var offsetX = 0f
    private var offsetY = 0f


    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (isBigScale) {
                isBigScale = false
                animator.reverse()
            } else {
                isBigScale = true
                // 根据点击中心放大
                // 注意：这里为什么是 bigScale / smallScale
                // 因为 bigScale 的计算值其实是根据原始图算的，但是触摸点的坐标是根据 smallScale 放大过的。
                offsetX = (e.x - centerX) * (1 - bigScale / smallScale)
                offsetY = (e.y - centerY) * (1 - bigScale / smallScale)

                animator.start()
            }
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (isBigScale) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return true
        }


        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            // 初始偏移量，加上速度，加上上限
            overScroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-minX).toInt(), minX.toInt(), (-minY).toInt(), minY.toInt(), dragOver, dragOver)
            postInvalidate()
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            // springBack 的逻辑跟 fling 一样，先设置完参数，接着 computeScrollOffset 计算结果，更新绘制即可。
            if (overScroller.springBack(offsetX.toInt(), offsetY.toInt(),
                            (-minX).toInt(), minX.toInt(), (-minY).toInt(), minY.toInt())) {
                postInvalidate()
            }
            return true
        }
    })


    private fun fixOffset() {
        offsetX = offsetX.coerceAtLeast(-minX - dragOver).coerceAtMost(minX + dragOver)
        offsetY = offsetY.coerceAtLeast(-minY - dragOver).coerceAtMost(minY + dragOver)
    }

    private val overScroller = OverScroller(context)

    override fun computeScroll() {
        super.computeScroll()
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            postInvalidateOnAnimation()
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapLeft = (width - bitmapWidth) / 2
        bitmapTop = (height - bitmapHeight) / 2
        centerX = width / 2
        centerY = height / 2
        bigScale = height / bitmapHeight.toFloat()
        smallScale = width / bitmapWidth.toFloat()
        scale = if (isBigScale) bigScale else smallScale

        minX = (bigScale * bitmapWidth - width) / 2f
        minY = 0f

        // 重置动画初始值
        animator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // scale = smallScale + (bigScale - smallScale) * fraction
        val fraction = (scale - smallScale) / (bigScale - smallScale)

        // offsetX = current + (target - current) * fraction

        val currentOffsetX = offsetX * fraction
        val currentOffsetY = offsetY * fraction
        canvas.translate(currentOffsetX, currentOffsetY)
        // 这里固定中心点是因为，offset 之后的边界会更好算。
        canvas.scale(scale, scale, centerX.toFloat(), centerY.toFloat())
        canvas.drawBitmap(bitmap, bitmapLeft.toFloat(), bitmapTop.toFloat(), paint)
    }

}