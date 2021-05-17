package com.ycq.homeworks.problems

import kotlin.math.max


class Solution53 {
    fun maxSubArray(nums: IntArray): Int {
        var result = 0
        var max = Int.MIN_VALUE
        nums.forEach {
            result += it
            max = max(max, result)
            if (result <= 0) {
                result = 0
            }
         
        }
        return max
    }
}