package com.ycq.homeworks.test

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ycq.homeworks.R
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById<TextView>(R.id.text)
        textView.text = paragraphSpace(testString, 50)
    }


}