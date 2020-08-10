package com.ycq.homeworks.draw.class15Animtor

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toPoint
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt

/**
 *
 */
class DrawAnimatorMoveCircle(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var radius = 50.dp
    private var centerX = 0
    private var centerY = 0

    /**
     * 注意，假如是用属性名，那么设置值的时候是通过反射实现的
     * 所以需要 public 的, 如果是 private 会有一个 warning
     * 但是很奇怪，上一个例子 ofFloat 用 private 就可以
     */
    var aaaa = Point()
        set(value) {
            field = value
            invalidate()
        }
    private val paint =  Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
    }

    private val evaluator = object : TypeEvaluator<Point>{
        override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point {
            return Point(
                ((endValue.x - startValue.x) * fraction + startValue.x).toInt(),
                ((endValue.x - startValue.x) * fraction + startValue.x).toInt()
            )
        }
    }
    private val property = object : Property<DrawAnimatorMoveCircle, Point>(Point::class.java,"") {

        override fun set(p: DrawAnimatorMoveCircle, value: Point) {
            p.aaaa = value
        }
        override fun get(p: DrawAnimatorMoveCircle): Point {
            return p.aaaa
        }

    }
    private val animator = ObjectAnimator.ofObject(this,  "aaaa",evaluator,
        Point(0,0), Point(200.dpInt, 200.dpInt) )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.apply {
            startDelay = 3000L
            start()
        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(aaaa.x - radius, aaaa.y.toFloat() - radius,
            aaaa.x + radius, aaaa.y + radius, paint)

    }
}