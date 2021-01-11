package com.ycq.homeworks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ycq.homeworks.test.TestFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, TestFragmentActivity::class.java))
        button?.setOnClickListener {
            startActivity(Intent(this, TestFragmentActivity::class.java))
        }

    }
}