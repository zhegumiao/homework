package com.ycq.homeworks.test

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.LineHeightSpan
import android.text.style.QuoteSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ycq.homeworks.R
import com.ycq.homeworks.util.dp
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * ParagraphSpace
 */
class TextViewParagraphSpaceFragment : Fragment() {
    companion object {
        fun paragraphSpace(text: String, spaceDp: Int): SpannableString {
            val formattedText: String = text.replace("\n", "\n\n")
            val spannableString = SpannableString(formattedText)

            val matcher: Matcher = Pattern.compile("\n\n").matcher(formattedText)
            while (matcher.find()) {
                spannableString.setSpan(
                    AbsoluteSizeSpan(spaceDp, true),
                    matcher.start() + 1,
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannableString
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_text_paragraph_space, container, false)
    }

    private lateinit var textView: TextView
    private val testString = "aaa\nbbb\nccc\nddd"
//    private val testString = "aaa\n\nbbb\n\nccc\n\nddd"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById<TextView>(R.id.text)
        val paragraphSpace = paragraphSpace(testString, 100)
//        textView.text = paragraphSpace.apply {
//            setSpan(
//                    CustomLineHeightSpan(100.dp),
//                    0, this.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        }
        textView.text = setSpan()
    }


    @SuppressLint("NewApi")
    fun setSpan(): SpannableString {
        // fixme 非常诡异的现象
        val text = "Text is\nspantastic\nspantastic"
        val spannable = SpannableString(text)
//        spannable.setSpan(
//                AbsoluteSizeSpan(200, true),
//                6, 7,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(
//                AbsoluteSizeSpan(200, true),
//                8, 9,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
                CustomLineHeightSpan(100.dp),
                0, 8,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}