package com.ycq.homeworks.util

import android.hardware.SensorManager
import android.view.ViewConfiguration

private const val INFLEXION = 0.35f // Tension lines cross at (INFLEXION, 1)
private val mFlingFriction = ViewConfiguration.getScrollFriction()
private var mPhysicalCoeff = -1f
private val DECELERATION_RATE = (Math.log(0.78) / Math.log(0.9)).toFloat()

fun getSplineFlingDistance(velocity: Float): Double {
    val l: Double = getSplineDeceleration(velocity)
    val decelMinusOne: Double = DECELERATION_RATE - 1.0
    return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l)
}

private fun getSplineDeceleration(velocity: Float): Double {
    if (mPhysicalCoeff < 0){
        mPhysicalCoeff = computeDeceleration(0.84f)
    }
    return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff).toDouble())
}

val mPpi = 1.dp * 160.0f
fun computeDeceleration(friction: Float): Float {
    return (SensorManager.GRAVITY_EARTH // g (m/s^2)
            * 39.37f // inch/meter
            * mPpi // pixels per inch
            * friction)
}