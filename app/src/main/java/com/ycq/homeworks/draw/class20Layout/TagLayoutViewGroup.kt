package com.ycq.homeworks.draw.class20Layout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getRandomColor
import com.ycq.homeworks.util.rnd

/**
 *
 */
class TagLayoutViewGroup constructor(context: Context, attrs: AttributeSet?) :
    ViewGroup(context, attrs) {

    companion object{
        private val size = 40
        private val data = (0..size).map {
            Data(it.toString(),
                getRandomColor(),
                rnd.nextInt(40.dpInt, 120.dpInt),
                rnd.nextInt(40.dpInt, 80.dpInt))
        }
    }

    init {
        data.forEach {
            addView(TextView(context).apply {
                text = it.text
//                background = GradientDrawable().apply {
//                    shape = GradientDrawable.RECTANGLE
//                    gradientRadius =
//                }
                setBackgroundColor(it.bgColor)
                // 由于着手动设置了 LayoutParams 所以不会生成默认的
                layoutParams = MarginLayoutParams(it.randomWidth, it.randomHeight)
                gravity = Gravity.CENTER
            })
        }
    }

    // 横向的测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        for (i in 0 until count){
            val view = getChildAt(i)
            measureChildWithMargins(view,
                widthMeasureSpec, 0,
                heightMeasureSpec, 0
            )
        }
//        setMeasuredDimension(
//            MeasureSpec.getSize(widthMeasureSpec),
//            MeasureSpec.getSize(heightMeasureSpec))
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (!changed) return
        val count = childCount
        val width = width
        var startHeight = 0
        var startWidth = 0
        var currentMaxHeight = 0
        for (i in 0 until count){
            val view = getChildAt(i)
            val childWidth = view.measuredWidth
            val childHeight = view.measuredHeight
            if (startWidth + childWidth > width){
                startHeight += currentMaxHeight
                startWidth = 0
                currentMaxHeight = 0
            }
            view.layout(startWidth, startHeight,
                startWidth + childWidth,
                startHeight + childHeight)
            currentMaxHeight = maxOf(currentMaxHeight, childHeight)
            startWidth += view.measuredWidth

        }
    }

//    override fun checkLayoutParams(p: LayoutParams?): Boolean {
//        return super.checkLayoutParams(p)
//    }
//    override fun generateDefaultLayoutParams(): LayoutParams {
//        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//    }
//    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
//        return MarginLayoutParams(p)
//    }
//
//    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
//        return super.generateLayoutParams(attrs)
//    }

}

data class Data(val text: String, val bgColor: Int, val randomWidth: Int, val randomHeight: Int)