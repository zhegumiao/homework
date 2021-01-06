package com.ycq.homeworks.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TextView(context: Context, attrs: AttributeSet?)
    : AppCompatTextView(context, attrs) {
    val paint =  Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }
    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        (0 until layout.lineCount).forEachIndexed { index, i ->
            val lineBaseline = layout.getLineBaseline(index)
            canvas.drawLine(0f, lineBaseline.toFloat(), width.toFloat(),
                (lineBaseline + 1).toFloat(), paint)
        }
    }
}