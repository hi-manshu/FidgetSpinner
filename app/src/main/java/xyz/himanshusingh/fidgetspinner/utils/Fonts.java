package xyz.himanshusingh.fidgetspinner.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by @himanshu on 5/16/2016.
 */
public class Fonts {

    public static void applyCustomFont(Context context, TextView textView,String customFont){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), customFont);
        textView.setTypeface(typeface);
    }
}

