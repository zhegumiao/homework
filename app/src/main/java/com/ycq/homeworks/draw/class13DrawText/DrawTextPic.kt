package com.ycq.homeworks.draw.class13DrawText

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar

/**
 * 这个是根据理解自己绘制的版本，跟视频的差异在于：
 *  1、PathDashPathEffect
 *  2、周长的计算用的 PathMeasure
 */
class DrawTextPic(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val text  = "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\""
    private var centerX: Int = 0
    private var centerY: Int = 0

    private val tSize = 20.dp
    private var adCenter = 0f
    private var ascent = 0f
    private var descent = 0f
    private var bottom = 0f
    private var top = 0f
    private var leading = 0f

    private val bitmap = getAvatar(50.dpInt)


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = tSize
        textAlign = Paint.Align.LEFT
        // 是先对于 baseLine 的高度

        ascent = fontMetrics.ascent
        descent = fontMetrics.descent
        bottom = fontMetrics.bottom
        top = fontMetrics.top
        leading = fontMetrics.leading
        adCenter = (ascent + descent)  / 2

    }

    private var bitmapOffsetX = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
        bitmapOffsetX = (width - bitmap.width).toFloat()

    }

    private var startIndex = 0
    private val endIndex = text.length
    private val floatArray = floatArrayOf()
    private var offsetY = -top

    private val bitmapOffsetY = 50.dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        while (startIndex < endIndex){
            var maxWidth = width.toFloat()
            if (offsetY + bottom > bitmapOffsetY && offsetY + top < bitmapOffsetY + bitmap.height){
                maxWidth = bitmapOffsetX
            }
            val breakTextSize = textPaint.breakText(text, startIndex, endIndex,
                true, maxWidth, floatArray)
            canvas.drawText(text, startIndex,  startIndex + breakTextSize,  0f,  offsetY, textPaint)
            offsetY += textPaint.fontSpacing
            startIndex += breakTextSize
        }
        canvas.drawBitmap(bitmap, bitmapOffsetX, bitmapOffsetY, textPaint)

    }
}