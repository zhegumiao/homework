package com.ycq.homeworks.draw.calss12xfermode

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.blue
import com.ycq.homeworks.R
import com.ycq.homeworks.util.decodeSampledBitmapFromResource
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar
import kotlin.math.cos
import kotlin.math.sin

/**
 * 两个 bitmap 的混合
 */
class DrawXfermode(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val radius = 100.dp
    private val circleWidth = 4.dp

    private var centerX: Int = 0
    private var centerY: Int = 0

    private val bitmap = getAvatar(200.dpInt)

    private val paint =  Paint(Paint.ANTI_ALIAS_FLAG)
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

    private val rect: RectF = RectF()
    private val rectInner: RectF = RectF()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
        rect.set(centerX - radius, centerY - radius,
            centerX + radius, centerY + radius)

        // 这里除以 2 是因为 STROKE 并不是内切，而是在中间
        val tmpRadius  = radius - circleWidth/2
        rectInner.set(centerX - tmpRadius, centerY - tmpRadius,
            centerX + tmpRadius, centerY + tmpRadius)

    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawOval(rect, paint.apply {
//            style  = Paint.Style.FILL
//            color = Color.BLUE
//        })
        canvas.drawOval(rect, paint.apply {
            strokeWidth = circleWidth
            style  = Paint.Style.STROKE
            color = Color.BLACK
        })
//        canvas.drawRect(rect, paint.apply {
//            style  = Paint.Style.STROKE
//            strokeWidth = 1f
//            color = Color.RED
//        })
        val saveLayer = canvas.saveLayer(rect, paint)
        paint.style  = Paint.Style.FILL
        canvas.drawOval(rectInner, paint)
        paint.run {
            xfermode = mXfermode
            canvas.drawBitmap(bitmap, centerX - radius, centerY - radius, paint)
            xfermode = null
        }
        canvas.restoreToCount(saveLayer)

    }
}