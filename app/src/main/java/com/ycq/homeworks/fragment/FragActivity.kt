package com.ycq.homeworks.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ycq.homeworks.R
import timber.log.Timber
import kotlin.random.Random

/**
 * refer: https://medium.com/androiddevelopers/the-android-lifecycle-cheat-sheet-part-iii-fragments-afc87d4f37fd
 */
class FragActivity: FragmentActivity() {
    var count = 0
    get() {
        field += 1
        return field
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag)
        // 这个地方会因为  restore 其实已经重建了，所以再次添加会覆盖在上面
        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                val transaction = beginTransaction()
                transaction.add(R.id.frag_activity_root,
                    FragmentSaveState().apply {
                        arguments = Bundle().apply {
                            // todo 这里可以改一改，用 random 还是不怎么好区分
                            putInt(FragmentSaveState.KEY_NUM, Random(System.currentTimeMillis()).nextInt(100))
                        }
                    }, null)
                transaction.commit()
            }
        }
        Timber.d("onCreate")
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d("onRestoreInstanceState")

    }
}
