package com.ycq.homeworks.test

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

class DividerWidthUtils(
    private val paddingLeft: Int,
    private val coverWidth: Int,
    private val coverPaddingRight: Int,
    private val dividerWidthMin: Int,
    private val dividerWidthMax: Int
) {

    private val coverSize = coverWidth + coverPaddingRight

    private fun calRangeList(screenWidth: Int, dividerWidth: Int): MutableList<IntRange> {

        var startWidth = paddingLeft
        val list = MutableList<IntRange>(0) { IntRange(0, 0) }
        while (startWidth < screenWidth) {
            val length = (coverWidth + coverPaddingRight + dividerWidth)
            val range = IntRange(startWidth, startWidth + length)
            list.add(range)
            startWidth += length
        }
        return list
    }

    fun calDividerWidth(screenWidth: Int): Float {
        val minRangeList = calRangeList(screenWidth, dividerWidthMin)
        val maxRangeList = calRangeList(screenWidth, dividerWidthMax)
        val minRange = maxRangeList.last()
        val min = maxRangeList.size - 1 + (screenWidth - minRange.first) / coverSize.toFloat()

        val maxRange = minRangeList.last()

        val max = minRangeList.size - 1 + (screenWidth - maxRange.first) / coverSize.toFloat()

        val maxFloat = ceil(max + 0.5f) - 0.5f
        val minFloat = floor(min - 0.5f) + 0.5f
        var start = maxFloat
        var minLength = abs(maxFloat - max)
        var result = max
        while (start in minFloat..maxFloat) {
            if (start > max) {
                val len = abs(start - max)
                if (len < minLength) {
                    minLength = len
                    result = max
                }
            } else if (start in min..max) {
                result = start
                break
            } else {
                val len = abs(start - min)
                if (len < minLength) {
                    minLength = len
                    result = min
                }
            }
            start -= 1f
        }
        val divider = (screenWidth - result * coverSize - paddingLeft) / floor(result)
        println("screenWidth=${screenWidth}, max=$max, min=$min, result=${result}, dividerWidth=$divider")
        return divider
    }
}

fun main() {
    val test = DividerWidthUtils(15, 65, 10, 5, 20)
    for (i in 300..420) {
        test.calDividerWidth(i)
    }
}