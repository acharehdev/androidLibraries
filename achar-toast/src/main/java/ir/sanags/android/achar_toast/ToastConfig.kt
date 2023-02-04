package ir.sanags.android.achar_toast

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment

object ToastConfig {
    var textTypeface: Typeface? = null
    var textSize: Float = 14f

    /** first: gravity, second: xOffset, third: yOffset */
    var gravityAndPosition: Triple<Int, Int, Int> = Triple(Gravity.CENTER_VERTICAL, 0, 0)

    /** first: background color, second: text color, third: icon */
    var successStyle: Triple<Int, Int, Int> =
        Triple(R.color.success_back, R.color.success_text, R.drawable.ic_outline_check_circle_24)

    /** first: background color, second: text color, third: icon */
    var errorStyle: Triple<Int, Int, Int> =
        Triple(R.color.error_back, R.color.error_text, R.drawable.ic_outline_cancel_24)

    /** first: background color, second: text color, third: icon */
    var InfoStyle: Triple<Int, Int, Int> =
        Triple(R.color.info_back, R.color.info_text, R.drawable.ic_outline_info_24)
}

fun Context?.toast(
    s: String?,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {

    if (this == null)
        return

    val layout = LayoutInflater.from(this).inflate(R.layout.custom_toast, null)
    val textView = layout.findViewById<TextView>(R.id.textView)

    val backgroundColor: Int
    val textColor: Int
    val imageResource: Int

    when (type) {
        ToastType.Success -> {
            backgroundColor =
                ContextCompat.getColor(this, ToastConfig.successStyle.first)
            textColor = ContextCompat.getColor(this, ToastConfig.successStyle.second)
            imageResource = ToastConfig.successStyle.third
        }
        ToastType.Error -> {
            backgroundColor = ContextCompat.getColor(this, ToastConfig.errorStyle.first)
            textColor = ContextCompat.getColor(this, ToastConfig.errorStyle.second)
            imageResource = ToastConfig.errorStyle.third
        }
        else -> {//ToastType.Info
            backgroundColor = ContextCompat.getColor(this, ToastConfig.InfoStyle.first)
            textColor = ContextCompat.getColor(this, ToastConfig.InfoStyle.second)
            imageResource = ToastConfig.InfoStyle.third
        }
    }

    (layout as CardView).setCardBackgroundColor(backgroundColor)
    textView.setTextColor(textColor)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
            textView,
            if (iconRight) 0 else imageResource,
            0,
            if (iconRight) imageResource else 0,
            0
        )
    } else {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            if (iconRight) 0 else imageResource,
            0,
            if (iconRight) imageResource else 0,
            0
        )
    }
    //imageView.setColorFilter(textColor, android.graphics.PorterDuff.Mode.SRC_IN)

    textView.text = s.toString().trim()

    textView.typeface = ToastConfig.textTypeface
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, ToastConfig.textSize)

    Toast(this).apply {
        setGravity(
            ToastConfig.gravityAndPosition.first,
            ToastConfig.gravityAndPosition.second,
            ToastConfig.gravityAndPosition.third
        )
        duration = length
        view = layout
    }.show()
}

fun Context?.toast(
    @StringRes res: Int,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {
    this.toast(this?.getString(res), type, length, iconRight)
}

fun Dialog.toast(
    s: String?,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {
    context.toast(s, type, length, iconRight)
}

fun Dialog.toast(
    @StringRes res: Int,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {
    context.toast(res, type, length, iconRight)
}

fun Fragment.toast(
    s: String?,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {
    context?.toast(s, type, length, iconRight)
}

fun Fragment.toast(
    @StringRes res: Int,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {
    context?.toast(context?.getString(res), type, length, iconRight)
}