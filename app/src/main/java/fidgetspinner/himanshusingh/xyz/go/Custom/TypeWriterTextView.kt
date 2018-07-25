package fidgetspinner.himanshusingh.xyz.go.Custom

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

class TypeWriterTextView : TextView {
    private var mText: CharSequence? = null
    private var mIndex: Int = 0
    private var mDelay: Long = 500 // Default 500ms character delay
    internal var animationCompleteCallBack: Handler? = null

    private val mHandler = Handler()
    private val characterAdder = object : Runnable {
        override fun run() {
            text = mText!!.subSequence(0, mIndex++)
            if (mIndex <= mText!!.length) {
                mHandler.postDelayed(this, mDelay)
            } else {
                if (null != animationCompleteCallBack)
                    animationCompleteCallBack!!.sendMessage(Message())
                else
                    Log.i(TAG, "Animation Complete Listener not set...")
            }
        }
    }

    constructor(context: Context) : super(context) {
        applyCustomFont(context, null!!)
    }

    fun setAnimationCompleteListener(animationCompleteCallBack: Handler) {
        this.animationCompleteCallBack = animationCompleteCallBack
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyCustomFont(context, attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        applyCustomFont(context, attrs)
    }

    fun animateText(text: CharSequence) {
        mText = text
        mIndex = 0
        setText("")
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }

    fun setCharacterDelay(millis: Long) {
        mDelay = millis
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

        private val TAG = TypeWriterTextView::class.java.simpleName
    }
}