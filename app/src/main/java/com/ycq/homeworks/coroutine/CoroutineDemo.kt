package com.ycq.homeworks.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 怎么使用 Coroutine Job SuperVisionJob
 * launch async
 * yield ensureActive
 * test
 */
class CoroutineDemo{
    val scope = CoroutineScope(Dispatchers.Main)
    fun test(){
        scope.launch(Dispatchers.IO) {  }
    }
}