package com.wang.taipeizooguide.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.*
import android.view.View

fun spannable(func: () -> SpannableString) = func()
private fun span(s: CharSequence, o: Any) =
    (if (s is String) SpannableString(s) else s as? SpannableString
        ?: SpannableString("")).apply { setSpan(o, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }

operator fun SpannableString.plus(s: SpannableString) = SpannableString(TextUtils.concat(this, s))
operator fun SpannableString.plus(s: String) = SpannableString(TextUtils.concat(this, s))


fun normal(s: CharSequence) = span(s, StyleSpan(Typeface.NORMAL))
fun bold(s: CharSequence) = span(s, StyleSpan(Typeface.BOLD))
fun italic(s: CharSequence) = span(s, StyleSpan(Typeface.ITALIC))
fun underline(s: CharSequence) = span(s, UnderlineSpan())
fun strike(s: CharSequence) = span(s, StrikethroughSpan())
fun sup(s: CharSequence) = span(s, SuperscriptSpan())
fun sub(s: CharSequence) = span(s, SubscriptSpan())
fun size(size: Int, s: CharSequence) = span(s, AbsoluteSizeSpan(size, true))

// color id must be the value retrieved by using ContextCompat.getColor(context, YOUR_CHOICE_OF_COLOR)
fun color(color: Int, s: CharSequence) = span(s, ForegroundColorSpan(color))
fun background(color: Int, s: CharSequence) = span(s, BackgroundColorSpan(color))
fun url(url: String, s: CharSequence): SpannableString = span(s, URLSpan(url))
fun bullet(s: CharSequence, gapWidth: Int, colorInt: Int) = span(s, BulletSpan(gapWidth, colorInt))

// color id must be the value retrieved by using ContextCompat.getColor(context, YOUR_CHOICE_OF_COLOR)
fun clickable(
    clickablePartColorInt: Int,
    clickablePart: String,
    onClickListener: () -> Unit
): SpannableString = with(normal(clickablePart)) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClickListener.invoke()
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.color = clickablePartColorInt
        }
    }
    val clickablePartStart = indexOf(clickablePart)
    setSpan(
        clickableSpan,
        clickablePartStart,
        clickablePartStart + clickablePart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}