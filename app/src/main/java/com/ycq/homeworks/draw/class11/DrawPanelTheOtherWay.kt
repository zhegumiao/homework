package com.ycq.homeworks.draw.class11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 根据视频调整的版本
 *  1、PathDashPathEffect
 *   intervalOff 的计算方式也有变化， 这个宽度是不被占据的，所以画笔的宽度设置也不一样
 *  2、周长的计算用的 PathMeasure
 */
class DrawPanelTheOtherWay(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val radius = 100.dp
    private val scaleLength = 10.dp
    private val scaleRadius = radius - scaleLength / 2
    private val scaleSize = 6
    private val scaleWidth = 2.dp
    private val startAngle = 150f
    private val sweepAngle = 240f
    private var centerX: Int = 0
    private var centerY: Int = 0


    private val currentPointerNumber = 6
    private val pointerLength  = 80.dp
    private val intervalDegree = sweepAngle / scaleSize
    // 注意这里需要转换坐标系
    private val pointerEnd  = PointF().apply {
        val angle = (360 - startAngle) - currentPointerNumber * intervalDegree
        x = (pointerLength * cos(Math.toRadians(angle.toDouble()))).toFloat()
        y = (pointerLength * sin(Math.toRadians(angle.toDouble()))).toFloat()
    }
    private var  intervalOff = 0f
//    private val intervals = floatArrayOf(scaleWidth, intervalOff.toFloat())
    private val path = Path()
    private val scalePath = Path().apply {
        addRect(0f, 0f, scaleWidth, scaleLength, Path.Direction.CW)
    }
    private val paint =  Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style  = Paint.Style.STROKE
        strokeWidth = 2.dp
    }

    private val paintScale =  Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style  = Paint.Style.STROKE
        // offset
//        pathEffect = PathDashPathEffect(scalePath, intervalOff, scaleWidth, PathDashPathEffect.Style.ROTATE)
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

        // 这里更那种方式有差别 主要是 PathDashPathEffect
        intervalOff = (PathMeasure(path, false).length - scaleWidth) / (scaleSize - 1)
        paintScale.pathEffect =
            PathDashPathEffect(scalePath, intervalOff, 0f, PathDashPathEffect.Style.ROTATE)

//        scalePath.addArc(
//            centerX - scaleRadius,
//            centerY - scaleRadius,
//            centerX + scaleRadius,
//            centerY + scaleRadius,
//            startAngle, sweepAngle)

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
        canvas.drawPath(path, paintScale)
        canvas.drawLine(centerX.toFloat(), centerY.toFloat(),
            // 这里 y 是减去 ，y 方向因为坐标系跟 sin cos 的坐标系相反
            centerX + pointerEnd.x, centerY - pointerEnd.y, paint)

    }
}