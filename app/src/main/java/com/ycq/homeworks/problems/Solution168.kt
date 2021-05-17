package com.ycq.homeworks.problems

import java.lang.StringBuilder

class Solution168 {
    final val list = listOf<String>("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    fun convertToTitle(columnNumber: Int): String {
        if (columnNumber <= 26) {
            return list[columnNumber - 1]
        }
        return convertToTitle(columnNumber / 26) + convertToTitle(columnNumber % 26)
    }
}