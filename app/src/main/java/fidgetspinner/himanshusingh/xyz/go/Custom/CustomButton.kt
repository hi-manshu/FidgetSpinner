package fidgetspinner.himanshusingh.xyz.go.Custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button

class CustomButton : Button {

    constructor(context: Context) : super(context) {
        setFont()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setFont()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setFont()
    }

    private fun setFont() {
        val normal = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Semibold.ttf")
        setTypeface(normal, Typeface.NORMAL)

        val bold = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Regular.ttf")
        setTypeface(bold, Typeface.BOLD)
    }


}