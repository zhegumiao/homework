package com.ycq.homeworks.draw.class11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp
import kotlin.math.cos
import kotlin.math.sin

class DrawPie(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    val colors = intArrayOf(Color.BLACK, Color.BLUE, Color.RED, Color.GRAY)
    val angeles = intArrayOf(30, 60, 120, 150)
    private val radius = 100.dp
    private val space = 8.dp

    private var centerX: Int = 0
    private var centerY: Int = 0
    private val paint =  Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for (i in colors.indices){
            paint.style = Paint.Style.FILL

            paint.color = colors[i]
            val sweepAngle = angeles[i].toFloat()
            val tempAngle = 360 - (startAngle + (sweepAngle / 2)).toDouble()
            val x = space * cos(Math.toRadians(tempAngle)).toFloat()
            val y = - space * sin(Math.toRadians(tempAngle)).toFloat()

            canvas.save()
            canvas.translate(x, y)
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle, sweepAngle, true, paint)
            canvas.restore()

            paint.style = Paint.Style.STROKE
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle, sweepAngle, true, paint)

            startAngle += sweepAngle

        }

    }
}