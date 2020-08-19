package com.ycq.homeworks.draw.class19Measure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import kotlin.math.min

/**
 *
 */
class CustomViewLayoutSizeView constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    init {
        outlineProvider = OvalOutline
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val min = min(measuredWidth, measuredHeight)
        setMeasuredDimension(min, min)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

}

val OvalOutline: ViewOutlineProvider = object : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(0, 0, view.width, view.height)
        outline.alpha = 1.0f
    }
}