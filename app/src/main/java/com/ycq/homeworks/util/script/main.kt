package com.ycq.homeworks.util.script

fun main() {
//  val list = DimenUtils.getListFromDimenArray(R.array.line_height_content)

    val list = listOf<Int>(30, 34, 36, 40, 43, 47, 50, 55, 61, 68, 75, 86)
    list.forEach {
        println("<item>${Math.round(it * 0.8f)}dp</item>")
    }

//  val list2 = listOf<Int>(12, 12, 12, 14, 14, 14, 16, 16, 16, 18, 18, 18)
//  list2.forEach {
//    println("<item>${Math.round(it * 1.5f)}dp</item>")
//  }
}