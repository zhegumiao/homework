package com.ycq.homeworks.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ycq.homeworks.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity ?: return
        val windowManager = activity!!.windowManager
        val window = activity!!.window
        toast.setOnClickListener {

        }
        toast_view.setOnClickListener {

        }
        window_toast_type.setOnClickListener {

        }
        window_application_type.setOnClickListener {

        }
        window_alert_type.setOnClickListener {

        }
    }

}