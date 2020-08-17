package com.ycq.homeworks.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ycq.homeworks.R
import timber.log.Timber
import kotlin.random.Random


/**
 *  Save State
 */
class FragmentSaveState: Fragment(){
    companion object{
        const val KEY_NUM = "NUM"
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }

    private var number  = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number = arguments?.getInt(KEY_NUM) ?: -1
        Timber.d("onCreate $number")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView $number")
        return inflater.inflate(R.layout.layout_frag, container, false).apply {
            val rnd = Random(hashCode())
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            setBackgroundColor(color)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.add).setOnClickListener {
            val count = (activity as FragActivity).count
            childFragmentManager
                .beginTransaction()
                .add(R.id.layout_root, FragmentSaveState().apply {
                    arguments = Bundle().apply {
                        putInt(KEY_NUM, count)
                    }
                })
                .addToBackStack(null)
                .commit()
        }
        view.findViewById<View>(R.id.pop).setOnClickListener {
            // todo 这里在多级 pop 的逻辑就不对了
            val fragActivity = activity as FragActivity
            fragActivity.count -= 1
            childFragmentManager
                .popBackStack()
        }

        view.findViewById<View>(R.id.add_new_activity).setOnClickListener {
            startActivity(Intent(context, FragActivity::class.java))
        }

        view.findViewById<TextView>(R.id.text).text = number.toString()
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.d("onViewStateRestored $number" )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView $number")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy $number")

    }

}