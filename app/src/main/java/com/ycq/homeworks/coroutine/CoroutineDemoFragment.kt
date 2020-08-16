package com.ycq.homeworks.coroutine

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar
import kotlinx.coroutines.*

/**
 * 怎么使用 Coroutine Job
 * SupervisorJob 的作用是：相对于普通的 job ，子 job 中如果有 非 CancellationException 的 uncaught exception
 * 普通 job 会让 parent job 和 其他 child job cancel。而 SuperVisorJob 则不会，并且可以在 parent job handler 或者
 * 子 job handler。
 *
 * launch async 都会在当前的 scope 下创建 child coroutine
 * launch 返回是代表这个 child coroutine 的一个 job
 * async 返回是一个 Deffer 对象，可以用 await 来获取返回值
 *
 * GlobalScope 创建的 coroutine 并没有 parent job
 *
 * use yield ensureActive 查看 Job 是否 cancel。 yield 会出让当前的执行机会给其他的 coroutine
 *
 * 另外，View 级别的组件，不应该有访问网络和协程之类的操作，因为 View 可以认为是不带生命周期管理的。
 * View 应该只用管 data 的展示和用户的交互
 * 网络和数据请求之类的操作应该放在有生命周期的组件内使用，如 Fragment 和 Activity
 *
 */
class CoroutineDemoFragment: Fragment() {

    /**
     * 当 Job failed 时，会导致 这个 scope 下所有 Job failed 。
     * 如果是 cancel 呢？只是 throw CancellationException 。parent coroutine 不会造成什么影响
     */
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    /**
     * 当 Job failure  时，不会 导致这个 scope 下所有 Job failed 。而是只有当前 Job
     * 如果是 cancel 呢？只是 throw CancellationException 。parent coroutine 不会造成什么影响
     */
    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob()) // MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    fun startScope(){
        mainScope.launch {
            delay(1000L)
            val asyncResult = async {
                delay(1000L)
                getData()
            }
            /**
             * await 是一个 suspend 方法，
             * 而且如果 async 有异常，也只会在 await 时抛出 这里是错的?
             * 所以需要在这 try catch
             */
            try {
                val result = asyncResult.await()
                println(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val image = async {
                getImage()
            }
            val bitmap = image.await()
            println("test" + bitmap?.width)
        }

    }

    /**
     *  withContext 是一个 suspend 方法，
     */
    suspend fun getData(): String {
        return withContext(Dispatchers.IO){
            delay(1000L)
            throw RuntimeException("test")
            return@withContext "im from getData"
        }
    }

    suspend fun getImage(): Bitmap? {
        return withContext(Dispatchers.IO){
            delay(1500L)
            return@withContext context?.getAvatar(200.dpInt)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    fun clear() {
        mainScope.cancel("main scope cancel")
        scope.cancel("normal scope cancel")
    }

}

fun main() = runBlocking {
    delay(1000L)
    /**
     * 这里在不同的 scope 环境下 async 对异常的处理不一样
     * root coroutines  忽略这个异常，如 GlobalScope https://kotlinlang.org/docs/reference/coroutines/exception-handling.html
     * CoroutineScope(Dispatchers.IO) 也是
     * 但是如 runBlocking 就需要 catch
     */
//    val asyncResult = CoroutineScope(Dispatchers.IO).async {
//        delay(1000L)
//        getData()
//    }

    // 包在外面不行
//    try {
//        val asyncResult1 = async {
//            delay(1000L)
//            getData()
//        }
//    }catch (e: java.lang.Exception){
//
//    }

    val asyncResult1 = async {
        delay(1000L)
        getData()
    }

    /**
     * await 是一个 suspend 方法，
     * 而且如果 async 有异常，也只会在 await 时抛出 这里是错的?
     * 所以需要在这 try catch
     */


    try {
        val result = asyncResult1.await()
        println(result)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    println("result")
}

suspend fun getData(): String {
    return withContext(Dispatchers.IO){
        delay(1000L)
        throw RuntimeException("test")
        return@withContext "im from getData"
    }
}