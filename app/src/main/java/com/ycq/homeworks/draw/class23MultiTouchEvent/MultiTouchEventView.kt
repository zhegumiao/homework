package com.ycq.homeworks.draw.class23MultiTouchEvent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.ycq.homeworks.views.MyView

/**
 * 手指的按下可以分为两类
 * 1、第一个手指按下
 * 2、非第一个手指按下
 * 这样就已经覆盖了所有的按下的情况
 * MotionEvent.ACTION_POINTER_DOWN 在这个事件中可以获得那个非第一个手指按下的 ID ，以及初始化一些状态。
 */
class MultiTouchEventView constructor(context: Context, attrs: AttributeSet?) :
        MyView(context, attrs) {
    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 非第一个手指按下
        MotionEvent.ACTION_POINTER_DOWN
        event.getPointerId(event.actionIndex)
        // 第一个手指按下
        MotionEvent.ACTION_DOWN


        val action = event.actionMasked
        println(MotionEvent.actionToString(action))
        return true
    }
}