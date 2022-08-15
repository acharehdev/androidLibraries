package ir.sanags.android.achar_toast

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment

fun Context?.toast(
    s: String?,
    type: ToastType,
    length: Int = Toast.LENGTH_LONG,
    iconRight: Boolean = true
) {

    if (this == null)
        return

    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.custom_toast, null)
    val textView = layout.findViewById<TextView>(R.id.textView)

    val backgroundColor: Int
    val textColor: Int
    val imageResource: Int

    when (type) {
        ToastType.Success -> {
            backgroundColor =
                ContextCompat.getColor(this, R.color.success_back)
            textColor = ContextCompat.getColor(this, R.color.success_text)
            imageResource = R.drawable.ic_outline_check_circle_24
        }
        ToastType.Error -> {
            backgroundColor = ContextCompat.getColor(this, R.color.error_back)
            textColor = ContextCompat.getColor(this, R.color.error_text)
            imageResource = R.drawable.ic_outline_cancel_24
        }
        else -> {//ToastType.Info
            backgroundColor = ContextCompat.getColor(this, R.color.info_back)
            textColor = ContextCompat.getColor(this, R.color.info_text)
            imageResource = R.drawable.ic_outline_info_24
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

    val toast = Toast(this)
    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
    toast.duration = length
    toast.view = layout
    toast.show()

}

fun Context?.toast(@StringRes res: Int, type: ToastType, length: Int = Toast.LENGTH_LONG) {
    this.toast(this?.getString(res), type, length)
}

fun Dialog.toast(s: String?, type: ToastType, length: Int = Toast.LENGTH_LONG) {
    context.toast(s, type, length)
}

fun Dialog.toast(@StringRes res: Int, type: ToastType, length: Int = Toast.LENGTH_LONG) {
    context.toast(res, type, length)
}

fun Fragment.toast(s: String?, type: ToastType, length: Int = Toast.LENGTH_LONG) {
    context?.toast(s, type, length)
}

fun Fragment.toast(@StringRes res: Int, type: ToastType, length: Int = Toast.LENGTH_LONG) {
    context?.toast(context?.getString(res), type, length)
}