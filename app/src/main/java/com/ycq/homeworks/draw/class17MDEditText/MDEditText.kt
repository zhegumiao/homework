package com.ycq.homeworks.draw.class17MDEditText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt

/**
 *
 */
class MDEditText(context: Context?, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {

    init {
        setPadding(paddingLeft, paddingTop + 20.dpInt, paddingRight, paddingBottom)
    }
    val localPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20.dp
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

    }

//        val text = hint ?: ""
//        canvas.drawText("text.toString()aaaaaaaaa", paddingLeft.toFloat(), paddingTop.toFloat(), localPaint)
}