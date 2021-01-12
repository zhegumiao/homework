package com.ycq.homeworks.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.ycq.homeworks.R
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt

/**
 * https://www.jianshu.com/p/35328f7ac54a
 *
 * 要计算字体的高度, 需要记住以下几点:
设置text size的时候是设置1Em的值
Roboto把1Em分成了2048份
在Roboto中, Ascent为1900, Descent为-500
在字体中, 基线(base line)是y=0的坐标轴
根据1, 2两点, 可以知道, 1份的值是(textSize / 2048) px, 假设text size是2048px, 那么1份就是1px.
而1900表示Ascent在基线上方, 距离是1900份. -500表示Descent在基线的下方, 距离是500份.
所以理论上, 如果在字体的text size是2048px, 那么对于这份Roboto Regular字体来说

ascender = 2048px / 2048 * 1900 = 1900px
// 同理
cap height = 1456px
x-height = 1082px
descender = 500px
总高度 = ascender + descender = 1900px + 500px = 2400px

作者：AssIstne
链接：https://www.jianshu.com/p/35328f7ac54a
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 */
class TextView(context: Context, attrs: AttributeSet?)
    : AppCompatTextView(context, attrs) {
    val paint =  Paint(Paint.ANTI_ALIAS_FLAG)
    val paintText =  TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        textSize = 10.dp
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        paint.color = Color.RED
        for (i in 0 .. height step 50.dpInt) {
            canvas.drawText("${i / 1.dp} dp", 0F, i.toFloat(), paintText )
            canvas.drawLine(0F, i.toFloat(),  width.toFloat(), i.toFloat(), paint)

        }
        paint.color = Color.BLUE
        (0 until layout.lineCount).forEachIndexed { index, i ->
            val lineBaseline = layout.getLineBaseline(index)
            val descent = layout.getLineDescent(index)
            val ascent = layout.getLineAscent(index)
            paint.color = Color.YELLOW
            canvas.drawLine(0f, (lineBaseline + ascent).toFloat(), width.toFloat(), (lineBaseline + ascent).toFloat(), paint)
            paint.color = Color.BLACK
            canvas.drawLine(0f, (lineBaseline + descent).toFloat(), width.toFloat(), (lineBaseline + descent).toFloat(), paint)

        }
    }
}