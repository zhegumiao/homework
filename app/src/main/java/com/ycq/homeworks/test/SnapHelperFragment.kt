package com.ycq.homeworks.test

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ycq.homeworks.R
import com.ycq.homeworks.recyclerview.GoodSnapHelper
import com.ycq.homeworks.util.dp

/**
 *
 */
class SnapHelperFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_snap_helper, container, false)
    }

    private var snapHelper = GoodSnapHelper(FastOutSlowInInterpolator())
    private lateinit var list: RecyclerView

    // 这几个感觉不出差别
    private val interpolatorList: List<Interpolator> = listOf(
        AccelerateDecelerateInterpolator(),
        FastOutSlowInInterpolator()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ColorAdapter(20)
            snapHelper.attachToRecyclerView(this)
        }
        view.setOnClickListener {
            val onFlingListener =
                list.onFlingListener as? GoodSnapHelper ?: return@setOnClickListener
            val text = onFlingListener.interpolator?.javaClass?.simpleName ?: "NULL"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }
        view.findViewById<RadioGroup>(R.id.radioGroup).apply {

            val button = AppCompatRadioButton(context).apply {
                layoutParams = RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "NULL"
                setOnClickListener {
                    snapHelper.attachToRecyclerView(null)
                    snapHelper = GoodSnapHelper(null)
                    snapHelper.attachToRecyclerView(list)

                }
            }
            addView(button)

            interpolatorList.forEach { interpolator ->
                val button = AppCompatRadioButton(context).apply {
                    layoutParams = RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    text = interpolator.javaClass.simpleName
                    setOnClickListener {
                        snapHelper.attachToRecyclerView(null)
                        snapHelper = GoodSnapHelper(interpolator)
                        snapHelper.attachToRecyclerView(list)

                    }
                }
                addView(button)
            }

        }

    }

}

class ColorAdapter(private val itemCount_: Int) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            textSize = 40.dp
            gravity = Gravity.CENTER
            requestLayout()
        }
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return itemCount_
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

}

class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {
    fun bindData(item: Int) {
        view.text = item.toString()
        val color = when (item % 5) {
            0 -> {
                Color.GREEN
            }
            1 -> {
                Color.WHITE
            }
            2 -> {
                Color.BLUE
            }
            3 -> {
                Color.RED
            }
            else -> {
                Color.GRAY
            }
        }
        view.setBackgroundColor(color)

    }
}
