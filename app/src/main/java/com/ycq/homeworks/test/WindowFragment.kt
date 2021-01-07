package com.ycq.homeworks.test

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ycq.homeworks.R
import com.ycq.homeworks.util.dp
import kotlinx.android.synthetic.main.frag_window_layer.*

/**
 * Window WindowManager
 */
class WindowFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_window_layer, container, false)
    }

    @SuppressLint("InlinedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity ?: return
        val windowManager = activity!!.windowManager
        val window = activity!!.window

        toast.setOnClickListener {
            Toast.makeText(activity!!, "just toast", Toast.LENGTH_LONG).show()
        }
        // 和 Toast 的效果一样
        toast_view.setOnClickListener {
            val view = TextView(activity!!, null).apply {
                text = "toast text"
            }
            Toast(activity!!).apply {
                this.view = view
                this.show()
            }
        }
        // bad token 的问题 不能使用
        window_toast_type.setOnClickListener {
            val view = TextView(activity!!, null).apply {
                text = "toast text"
                setTextColor(Color.BLUE)
            }
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = 30.dp.toInt()
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
            windowManager.addView(view, layoutParams)
        }
        // 应用上
        window_application_type.setOnClickListener {
            val view = TextView(activity!!, null).apply {
                text = "toast text"
                setTextColor(Color.BLUE)
            }
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = 30.dp.toInt()
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            windowManager.addView(view, layoutParams)

        }
        // 需要权限
        window_alert_type.setOnClickListener {
            val view = TextView(activity!!, null).apply {
                text = "toast text"
                setTextColor(Color.BLUE)
            }
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = 30.dp.toInt()
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            windowManager.addView(view, layoutParams)

        }
    }

}