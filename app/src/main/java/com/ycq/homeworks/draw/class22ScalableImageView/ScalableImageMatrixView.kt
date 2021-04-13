package com.ycq.homeworks.draw.class22ScalableImageView

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.OverScroller
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar
import com.ycq.homeworks.views.MyView

/**
 * 使用 matrix 来实现变换
 */
class ScalableImageMatrixView constructor(context: Context, attrs: AttributeSet?) :
    MyView(context, attrs) {
    private val bitmap = getAvatar(50.dpInt)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmapWidth = bitmap.width
    private val bitmapHeight = bitmap.height

    private val bitmapRectF = RectF()
    private var centerX = 0f
    private var centerY = 0f

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

    private val dragOver = 20.dpInt

    var currentScale = 1f

    var animatorScale = 1f
        set(value) {
            field = value
            aScaleFunc(doubleTapX, doubleTapY, field / currentScale, centerX, centerY, mat)
            currentScale = field
            invalidate()
        }

    private val animator = ObjectAnimator.ofFloat(this, "animatorScale", smallScale, bigScale).apply {
        duration = 800
    }

    private var offsetX = 0f
    private var offsetY = 0f

    private var doubleTapX = 0f
    private var doubleTapY = 0f


    private fun calOffset(x : Float, y : Float) {
        // 根据点击中心放大
        // 注意：这里为什么是 bigScale / smallScale
        // 因为 bigScale 的计算值其实是根据原始图算的，但是触摸点的坐标是根据 smallScale 放大过的。
        offsetX = (x- centerX) * (1 - bigScale / smallScale)
        offsetY = (y - centerY) * (1 - bigScale / smallScale)
    }


    private fun fixOffset() {
        offsetX = offsetX.coerceAtLeast(-minX - dragOver).coerceAtMost(minX + dragOver)
        offsetY = offsetY.coerceAtLeast(-minY - dragOver).coerceAtMost(minY + dragOver)
    }

    private val overScroller = OverScroller(context)

    override fun computeScroll() {
        super.computeScroll()
        if (overScroller.computeScrollOffset()) {
            val oldX = offsetX
            val oldY = offsetY
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            mat.postTranslate(offsetX - oldX, offsetY - oldY)
            postInvalidateOnAnimation()
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapRectF.apply {
            left = (width - bitmapWidth) / 2f
            top = (height - bitmapHeight) / 2f
            right = (height + bitmapHeight) / 2f
            bottom = (height + bitmapHeight) / 2f
        }
        centerX = width / 2f
        centerY = height / 2f

        doubleTapX = centerX
        doubleTapY = centerY

        bigScale = height / bitmapHeight.toFloat()
        smallScale = width / bitmapWidth.toFloat()

        animatorScale = if (isBigScale) bigScale else smallScale
        minX = (bigScale * bitmapWidth - width) / 2f
        minY = 0f

    }

    private val mat = Matrix()
    private val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val touchX = detector.focusX
            val touchY = detector.focusY
            val s = detector.scaleFactor
            aScaleFunc(touchX, touchY, s, centerX, centerY, mat)
            currentScale *= s
            invalidate()
            return true
        }

    })

    private fun resetMat() {
        mat.reset()

    }

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            invalidate()
        }
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (isBigScale) {
                isBigScale = false
                doubleTapX = e.x
                doubleTapY = e.y
                animator.setFloatValues(smallScale, currentScale)
                animator.reverse()
            } else {
                isBigScale = true
                doubleTapX = e.x
                doubleTapY = e.y
                animator.setFloatValues(currentScale, bigScale)
                animator.start()
            }
            return true
        }

//        private val bitmapOutRectF = RectF()

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (isBigScale) {
                val oldX = offsetX
                val oldY = offsetY
                offsetX -= distanceX
                offsetY -= distanceY
//                mat.mapRect(bitmapOutRectF, bitmapRectF)
//                mat.mapPoints(floatArray)
                fixOffset()

                mat.postTranslate(offsetX - oldX, offsetY - oldY)
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.setMatrix(mat)
        canvas.drawBitmap(bitmap, bitmapRectF.left, bitmapRectF.top, paint)
        canvas.restore()
    }


    private  val floatArray by lazy {
        floatArrayOf(centerX, centerY)
    }
    private  val floatOutArray = floatArrayOf(0f, 0f)
    /**
     * 这里 centerX centerY 其实可以是任意值
     * scale 是当前状态是前一状态的比值
     */
    private fun aScaleFunc(focusX : Float, focusY : Float, scale : Float, relatedCenterX : Float, relatedCenterY : Float, matrix: Matrix) {

//        val dx = (focusX - relatedCenterX) * (1 - scale)
//        val dy = (focusY - relatedCenterY) * (1 - scale)
//
//
//        // 这里为什么要乘以 scale，因为要跟绘制的设定一样，还要 scale 之前的结果
//        offsetX *= scale
//        offsetY *= scale
//        val oldX = offsetX
//        val oldY = offsetY
//        offsetX += dx
//        offsetY += dy
//
//
//        fixOffset()
//        matrix.postScale(scale, scale, relatedCenterX, relatedCenterY)
//        matrix.postTranslate(offsetX - oldX, offsetY - oldY)


        val dx = (focusX - relatedCenterX) * (1 - scale)
        val dy = (focusY - relatedCenterY) * (1 - scale)

        // 更好的办法是用 mat 来更新

        mat.mapPoints(floatOutArray, floatArray)
        val oldX = floatOutArray[0] - centerX
        val oldY = floatOutArray[1] - centerY

        offsetX += dx
        offsetY += dy
        fixOffset()
        matrix.postScale(scale, scale, relatedCenterX, relatedCenterY)
        matrix.postTranslate(offsetX - oldX, offsetY - oldY)

        mat.mapPoints(floatOutArray, floatArray)
        offsetX = floatOutArray[0] - centerX
        offsetY = floatOutArray[1] - centerY
    }


}