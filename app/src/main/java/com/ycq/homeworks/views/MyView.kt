package com.ycq.homeworks.views

import android.content.Context
import android.view.View

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import com.ycq.homeworks.util.dp

open class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        textSize = 10.dp
        textAlign = Paint.Align.CENTER
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        for (i in 0..3000 step 100) {
            canvas.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), paint)
            canvas.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), paint)
            canvas.drawText("$i", i.toFloat(), i.toFloat(), paint)
        }

    }
}