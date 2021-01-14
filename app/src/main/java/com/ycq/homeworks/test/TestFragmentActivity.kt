package com.ycq.homeworks.test

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ycq.homeworks.R
import timber.log.Timber

/**
 * 作为测试的 base
 */
class TestFragmentActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag)
        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                val transaction = beginTransaction()
                transaction.add(R.id.frag_activity_root,
                    SnapHelperFragment(), null)
                transaction.commit()
            }
        }
        Timber.d("onCreate")
    }

}
