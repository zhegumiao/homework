package com.ycq.homeworks.lint

import androidx.annotation.IntDef

internal class TestLint {
    @IntDef(V1, V2, V3)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    private annotation class TestClassName

    fun testFun(@TestClassName value: Int) {}
    fun init() {
        // warning: not working in kotlin
        testFun(5)
    }

    companion object {
        const val V1 = 1
        const val V2 = 2
        const val V3 = 3
    }
}