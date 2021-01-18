package com.ycq.homeworks.lint

import androidx.annotation.IntDef

internal class TestLint {
    @IntDef(V1, V2, V3)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    private annotation class TestClassName

    fun testFun(@TestClassName value: Int) {}
    fun init() {
        // 编译时不会又 warning 提示
        // 但是这里直接运行 lint 时会不通过
        testFun(5)
    }

    companion object {
        const val V1 = 1
        const val V2 = 2
        const val V3 = 3
    }
}