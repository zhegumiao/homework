package com.ycq.homeworks.draw.class17MDEditText

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.ycq.homeworks.util.dp
import com.ycq.homeworks.util.dpInt

/**
 * 这里不用两个参数的构造方法，会有问题，连焦点都获取不到
 * 因为两个参数的构造方法里面加了默认主题
 * 而且用 @JvmOverloads 都最终会调用三个参数的方法
 */
class MDEditText constructor(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {



    private val extraPadding = 20.dpInt
    init {
        setPadding(paddingLeft, paddingTop + extraPadding, paddingRight, paddingBottom)
    }
    val localPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15.dp
    }


    var topPadding = 0
        set(value){
            field = value
            invalidate()
        }
    private val topPaddingAnimator = ObjectAnimator.ofInt(
        this,
        "topPadding",
        extraPadding, (-extraPadding / 2))

    var userAlpha = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val alphaAnimator = ObjectAnimator.ofFloat(
        this,
        "userAlpha",
        0f, 255f)

    private val animatorSet = AnimatorSet().apply {
        playTogether(topPaddingAnimator, alphaAnimator)
    }


    private var hasTextBefore = !editableText.isNullOrEmpty()
    @SuppressLint("NewApi")
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val hasTextNow = !text.isNullOrEmpty()

        if (hasTextNow && !hasTextBefore){
            animatorSet.start()
        }else if (hasTextBefore && !hasTextNow){
            animatorSet.reverse()
        }
        hasTextBefore = hasTextNow

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val text = hint ?: ""
        localPaint.alpha = userAlpha.toInt()
        canvas.drawText(
            text.toString(),
            paddingLeft.toFloat(),
            (paddingTop + topPadding).toFloat(),
            localPaint)
    }

}