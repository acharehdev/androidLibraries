package ir.sanags.android.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat


/**
 * CategorizedKeyValueLayout
 */
class CategorizedKeyValueLayout : LinearLayout {

    private lateinit var textViewCategoryName: AppCompatTextView
    private lateinit var textViewContainer: TextView

    private var _categoryName: String? = null
    private var _categoryNameColor: Int = Color.GREEN
    private var _categoryNameDimension: Float = 0f
    private var _contentKey: String? = null
    private var _contentValue: String? = null

    private var cardCornerRadius: Int = 0

    /**
     * The text to draw
     */
    var categoryName: String?
        get() = _categoryName
        set(value) {
            _categoryName = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var categoryNameColor: Int
        get() = _categoryNameColor
        set(value) {
            _categoryNameColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var categoryNameDimension: Float
        get() = _categoryNameDimension
        set(value) {
            _categoryNameDimension = value
            invalidateTextPaintAndMeasurements()
        }

    var content: String?
        get() = _contentKey
        set(value) {
            _contentKey = value
            invalidateTextPaintAndMeasurements()
        }

    var contentValue: String?
        get() = _contentValue
        set(value) {
            _contentValue = value
            invalidateTextPaintAndMeasurements()
        }

    /*private fun parseMap(value: Map<String?, String?>) {
        val str = SpannableBuilder()
        for (item in value) {
            item.key
        }
    }*/

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        // Load attributes
        //orientation = VERTICAL
        orientation = VERTICAL
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.root_categorized_key_value_layout, this, true)

        textViewCategoryName = view.findViewById(R.id.tvCategoryName)

        textViewContainer = view.findViewById(R.id.textViewContainer)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CategorizedKeyValueLayout, defStyleAttr, 0
        )

        _categoryName = a.getString(
            R.styleable.CategorizedKeyValueLayout_categoryName
        )
        _categoryNameColor = a.getColor(
            R.styleable.CategorizedKeyValueLayout_categoryNameColor,
            Color.GREEN
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _categoryNameDimension = a.getDimension(
            R.styleable.CategorizedKeyValueLayout_categoryNameDimension,
            14f
        )

        _contentKey = a.getString(
            R.styleable.CategorizedKeyValueLayout_contentKey
        )

        _contentValue = a.getString(
            R.styleable.CategorizedKeyValueLayout_contentValue
        )

        a.recycle()

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    fun clear() {
        textViewContainer.text = null
    }


    /*override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        linearLayout.layout(l, t, r, b)
    }*/


    private fun invalidateTextPaintAndMeasurements() {
        textViewCategoryName.apply {
            text = categoryName
            setTextColor(categoryNameColor)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, categoryNameDimension)
        }

        setContent(_contentKey, _contentValue)
    }

    fun setStrContent(str: String) {
        textViewContainer.text = str
    }

    fun appendStrContent(str: String) {
        textViewContainer.append(str)
    }

    //public functions
    fun setContent(
        key: String?,
        value: String?,
        afterValue: String = ""
    ) {
        textViewContainer.text = createSpannableKeyValue(key, value, afterValue)
    }

    fun appendContent(
        key: String?,
        value: String?,
        afterValue: String = ""
    ) {
        textViewContainer.append(createSpannableKeyValue(key, value, afterValue))
    }


    private fun createSpannableKeyValue(
        key: String?,
        value: String?,
        afterValue: String = ""
    ): Spannable {
        var tempKey = ""
        var tempValue = ""

        key?.let { tempKey = it }
        value?.let { tempValue = it }

        val spannable = SpannableString(tempKey + tempValue + afterValue)

        //spannable.append(title, RelativeSizeSpan(1.25f), Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            (tempKey.length),
            (tempKey + tempValue).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF3D4166")),
            tempKey.length,
            (tempKey + tempValue).length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        spannable.setSpan(
            RelativeSizeSpan(1.1f),
            tempKey.length,
            (tempKey + tempValue).length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannable
    }

}