package com.ycq.homeworks.draw.class13DrawText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp

class DrawTextInCircleCenter(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var centerX: Int = 0
    private var centerY: Int = 0
    private val radius = 100.dp
    private val path  = Path()
    private val startAngle = 150f
    private val sweepAngle = 240f

    private val text = "AbcdgGF"
    private val tSize = 40.dp
    private var adCenter = 0f
    private var ascent = 0f
    private var descent = 0f
    private var bottom = 0f
    private var top = 0f
    private var leading = 0f


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20.dp
        strokeCap = Paint.Cap.ROUND
    }


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = tSize
        textAlign = Paint.Align.CENTER
        // 是先对于 baseLine 的高度

        ascent = fontMetrics.ascent
        descent = fontMetrics.descent
        bottom = fontMetrics.bottom
        top = fontMetrics.top
        leading = fontMetrics.leading

        adCenter = (ascent + descent)  / 2

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
        path.addArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            startAngle, sweepAngle)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius, paint)
        paint.color = Color.RED
        canvas.drawPath(path, paint)
        canvas.drawLine(centerX - radius, centerY.toFloat(), centerX + radius,
            centerY.toFloat(), textPaint)
        canvas.drawLine(centerX - radius, centerY + bottom, centerX + radius,
            centerY + bottom, textPaint)
        canvas.drawLine(centerX - radius, centerY + descent, centerX + radius,
            centerY + descent, textPaint)
        canvas.drawLine(centerX - radius, centerY + ascent, centerX + radius,
            centerY + ascent, textPaint)
        canvas.drawLine(centerX - radius, centerY + top, centerX + radius,
            centerY + top, textPaint)

        canvas.drawText(text, centerX.toFloat(), centerY - adCenter, textPaint)

    }
}