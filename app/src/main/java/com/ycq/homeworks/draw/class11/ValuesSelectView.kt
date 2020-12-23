package com.ycq.homeworks.draw.class11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.FloatRange
import com.ycq.homeworks.util.dp

/**
 *
 */
class ValuesSelectView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val valueList = listOf(15, 17, 19, 21, 23)
    private val selectorRadius = 20.dp
    private val itemRadius = 5.dp
    private val lineHeight = 1.dp

    private val selectorColor = Color.BLUE
    private val itemColor = Color.GREEN
    private val lineColor = Color.GRAY

    private val selectorRectF = RectF()
    private val itemRectFList = ArrayList<RectF>()

    private val itemRangeList = ArrayList<ClosedFloatingPointRange<Float>>()

    private var currentIndex = 1
    private var stepSize = 0
    private var heightCenter = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        heightCenter = height / 2f
        // line
        lineStartX = 0f
        lineStartY = heightCenter
        lineStopX = width.toFloat()
        lineStopY = heightCenter
        // items
        stepSize = width / (valueList.size - 1)
        val halfStep = stepSize / 2f

        itemRectFList.clear()
        itemRangeList.clear()
        valueList.forEachIndexed { index, i ->
            val centerX = (index * stepSize).toFloat()
            val rectF = RectF().apply {
                left = centerX - itemRadius
                top = heightCenter - itemRadius
                right = centerX + itemRadius
                bottom = heightCenter + itemRadius

            }
            itemRectFList.add(rectF)

            // range
            val startX = centerX - halfStep
            val range = startX.rangeTo(startX + stepSize)
            itemRangeList.add(range)
        }

        // selector
        calSelectorRectF()
    }

    private fun calSelectorRectF() {
        selectorRectF.apply {
            val centerX = (currentIndex * stepSize).toFloat()
            left = centerX - selectorRadius
            top = heightCenter - selectorRadius
            right = centerX + selectorRadius
            bottom = heightCenter + selectorRadius
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_CANCEL -> {

            }
            else -> {
                // 算出最近的 item 的 index
                currentIndex = getNearItemIndex(event.x)
                calSelectorRectF()
                invalidate()
            }
        }

        return true

    }

    private fun getNearItemIndex(x: Float): Int {
        var result = 0
        itemRangeList.forEachIndexed { index, range ->
            if (range.contains(x)) {
                result = index
                return@forEachIndexed
            }
        }
        return result
    }

    private var lineStartX = 0f
    private var lineStartY = 0f
    private var lineStopX = 0f
    private var lineStopY = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = lineColor
        strokeWidth = lineHeight
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, paint)
    }

    private val selectorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private fun drawItems(canvas: Canvas, rectFList: List<RectF>) {
        selectorPaint.apply {
            color = itemColor
        }
        rectFList.forEach {
            canvas.drawOval(it, selectorPaint)
        }
    }

    private fun drawSelector(canvas: Canvas) {
        selectorPaint.apply {
            color = selectorColor
        }
        canvas.drawOval(selectorRectF, selectorPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(canvas)
        drawItems(canvas, itemRectFList)
        drawSelector(canvas)
    }
}