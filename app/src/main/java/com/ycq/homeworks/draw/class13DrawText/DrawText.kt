package com.ycq.homeworks.draw.class13DrawText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp

class DrawText(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var centerX: Int = 0
    private var centerY: Int = 0
    private val radius = 100.dp
    private val path  = Path()
    private val startAngle = 150f
    private val sweepAngle = 240f


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20.dp
        strokeCap = Paint.Cap.ROUND
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
        

    }
}