package fidgetspinner.himanshusingh.xyz.go.Custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by himanshusingh on 07/06/17.
 */

class CustomFontTextView : TextView {

    constructor(context: Context) : super(context) {

        applyCustomFont(context, null!!)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        applyCustomFont(context, attrs)
    }

    private fun applyCustomFont(context: Context, attrs: AttributeSet) {
        val textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)
        val customFont = selectTypeface(context, textStyle)
        typeface = customFont
    }

    private fun selectTypeface(context: Context, textStyle: Int): Typeface {
        when (textStyle) {
            Typeface.BOLD // bold
            -> return Typeface.createFromAsset(context.assets, "fonts/OpenSans-Semibold.ttf")
            Typeface.ITALIC // italic
            -> return Typeface.createFromAsset(context.assets, "fonts/OpenSans-Regular.ttf")
            Typeface.BOLD_ITALIC // bold italic
            -> return Typeface.createFromAsset(context.assets, "fonts/OpenSans-Semibold.ttf")
            Typeface.NORMAL // regular
            -> return Typeface.createFromAsset(context.assets, "fonts/OpenSans-Regular.ttf")
            else -> return Typeface.createFromAsset(context.assets, "fonts/OpenSans-Regular.ttf")
        }
    }

    companion object {

        val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
    }
}
