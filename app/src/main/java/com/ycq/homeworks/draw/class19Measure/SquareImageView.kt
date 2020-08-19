package com.ycq.homeworks.draw.class19Measure

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar
import kotlin.math.min

/**
 *
 */
class SquareImageView constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getAvatar(200.dpInt)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val min = min(measuredWidth, measuredHeight)
        setMeasuredDimension(min, min)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, left.toFloat(), top.toFloat(), paint)
    }

}