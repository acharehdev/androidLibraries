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
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView


/**
 * CategorizedKeyValueLayout
 */
class CategorizedKeyValueLayout : LinearLayout {
    // views
    private lateinit var textViewCategoryName: AppCompatTextView
    private lateinit var textViewContainer: TextView
    private lateinit var cardView: MaterialCardView


    // strings
    /**
     * The text of textViewCategoryName
     */
    var categoryName: String? = null
        set(value) {
            field = value
            textViewCategoryName.text = value
        }

    var contentKeyValue: String? = null
        get() {
            return textViewContainer.text.toString()
        }
        set(value) {
            field = value
            textViewContainer.text = value
        }

    // colors
    /**
     * The color of textViewCategoryName
     */
    var categoryNameColor: Int = 0
        set(value) {
            field = value
            textViewCategoryName.setTextColor(value)
        }

    var contentColor: Int = 0
        set(value) {
            field = value
            textViewContainer.setTextColor(value)
        }

    var contentKeyColor: Int = 0

    var contentValueColor: Int = 0


    // dimensions and sizes
    /**
     * The font size of textViewCategoryName
     */
    var categoryNameDimension: Float = 0F
        set(value) {
            field = value
            textViewCategoryName.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    var cardCornerRadius: Float = 0F
        set(value) {
            field = value
            cardView.radius = field
        }

    var categoryNameFont: Typeface? = null
        set(value) {
            field = value
            textViewCategoryName.typeface = value
        }

    var contentFont: Typeface? = null
        set(value) {
            field = value
            textViewContainer.typeface = value
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
        cardView = view.findViewById(R.id.cardView)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CategorizedKeyValueLayout, defStyleAttr, 0
        )

        categoryName = a.getString(
            R.styleable.CategorizedKeyValueLayout_categoryName
        )
        contentKeyValue = a.getString(
            R.styleable.CategorizedKeyValueLayout_contentKeyValue
        )
        val contentKey = a.getString(
            R.styleable.CategorizedKeyValueLayout_contentKey
        )
        val contentValue = a.getString(
            R.styleable.CategorizedKeyValueLayout_contentValue
        )

        categoryNameColor = a.getColor(
            R.styleable.CategorizedKeyValueLayout_categoryNameColor,
            Color.parseColor("#00BFA5")
        )
        contentColor = a.getColor(
            R.styleable.CategorizedKeyValueLayout_contentColor,
            Color.parseColor("#3D4166")
        )
        contentKeyColor = a.getColor(
            R.styleable.CategorizedKeyValueLayout_contentKeyColor,
            Color.parseColor("#3D4166")
        )
        contentValueColor = a.getColor(
            R.styleable.CategorizedKeyValueLayout_contentValueColor,
            Color.parseColor("#3D4166")
        )


        categoryNameDimension = a.getDimension(
            R.styleable.CategorizedKeyValueLayout_categoryNameDimension,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                14f,
                context.resources.displayMetrics
            )
        )
        cardCornerRadius = a.getDimension(
            R.styleable.CategorizedKeyValueLayout_cardCornerRadius,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10f,
                context.resources.displayMetrics
            )
        )

        a.getResourceId(R.styleable.CategorizedKeyValueLayout_categoryNameFont, 0).let {
            if (it > 0) {
                categoryNameFont = ResourcesCompat.getFont(context, it)
            }
        }
        a.getResourceId(R.styleable.CategorizedKeyValueLayout_contentFont, 0).let {
            if (it > 0) {
                contentFont = ResourcesCompat.getFont(context, it)
            }
        }

        a.recycle()

        if ((contentKey?.length ?: 0) + (contentValue?.length ?: 0) != 0) {
            setContent(contentKey, contentValue)
        }
    }

    fun clear() {
        textViewContainer.text = null
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

        spannable.setSpan(
            ForegroundColorSpan(contentKeyColor),
            0,
            tempKey.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        //spannable.append(title, RelativeSizeSpan(1.25f), Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            (tempKey.length),
            (tempKey + tempValue).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(contentValueColor),
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