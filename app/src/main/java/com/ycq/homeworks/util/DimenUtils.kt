package com.ycq.homeworks.util

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.core.content.res.getIntOrThrow

object DimenUtils {
    fun  getListFromDimenArray(context: Context, @ArrayRes resId: Int): MutableList<Int> {
        val obtainTypedArray = context.resources.obtainTypedArray(resId)
        val array = mutableListOf<Int>()
        (0 until obtainTypedArray.indexCount).forEachIndexed { index, i ->
            val int = obtainTypedArray.getIntOrThrow(i)
            array.add(int)
        }
        obtainTypedArray.recycle()
        return array
    }
}