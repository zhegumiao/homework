package com.ycq.homeworks.draw.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.getSplineFlingDistance

/**
 *
 */
class SpeedDistanceView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val maxSpeed = 20000
    val speedRate = 0.1f
    val distanceRate = 0.1f
    private val mStep = 10
    private val data = FloatArray((maxSpeed / mStep) * 4)
    private var mWidth = 1080
    private var screenCount = 1

    // y = x * lineRate
    // x = y / lineRate
    // x * lineRate = getSplineFlingDistance(y / lineRate)
    private val itemRangeList = HashMap<ClosedFloatingPointRange<Float>, Float>()

    // 0 - 2000          0
    // 2000 - 6000       1
    // 6000 - 12000      2
    private var lineRate = 1f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        var startX = 0f
        var startY: Double = 0.0
        var lastDistance = 0f
        for ((currentIndex, i) in (1..maxSpeed step mStep).withIndex()) {
            val distance = getSplineFlingDistance(i.toFloat())
            data[currentIndex * 4 + 0] = startX
            data[currentIndex * 4 + 1] = startY.toFloat()
            startX = (i * speedRate)
            startY = (distance * distanceRate)
            data[currentIndex * 4 + 2] = startX
            data[currentIndex * 4 + 3] = startY.toFloat()
            if (mWidth * screenCount + 10 >= distance && distance >= mWidth * screenCount - 10) {
                val rangeTo = lastDistance.rangeTo(i.toFloat())
                println("test: i = $i")
                screenCount++
            }
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, 0f, 2000f, 2000f, paint)
        for (i in 0..height step 100) {
            canvas.drawLine(0F, i.toFloat(), width.toFloat(), i.toFloat(), paint)

        }
        canvas.drawLines(data, paint)
    }
}