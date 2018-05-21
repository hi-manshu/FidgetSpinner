package xyz.himanshusingh.fidgetspinner.Custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton  extends Button {

    public CustomButton(Context context) {
        super( context );
        setFont();

    }

    public CustomButton(Context context, AttributeSet attrs) {
        super( context, attrs );
        setFont();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans-Semibold.ttf");
        setTypeface( normal, Typeface.NORMAL );

        Typeface bold = Typeface.createFromAsset( getContext().getAssets(), "fonts/OpenSans-Regular.ttf" );
        setTypeface( bold, Typeface.BOLD );
    }




}