package com.ycq.homeworks.draw.class15Animtor

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp

/**
 *
 */
class DrawAnimatorScaleCircle(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var radius = 50.dp
        set(value) {
            field = value
            invalidate()
        }

    private var centerX = 0
    private var centerY = 0
    private val paint =  Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
    }

    private val animator = ObjectAnimator.ofFloat(this, "radius", 50.dp, 150.dp)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.apply {
            startDelay = 3000L
            start()
        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(centerX - radius, centerY.toFloat() - radius,
            centerX + radius, centerY + radius, paint)

    }
}