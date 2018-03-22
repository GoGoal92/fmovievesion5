package market.goldandgo.videonew1.API;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Go Goal on 3/20/2017.
 */

public class adobetextview extends TextView {

    public adobetextview(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "adobe.otf");
        this.setTypeface(face);
    }

    public adobetextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "adobe.otf");
        this.setTypeface(face);
    }

    public adobetextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "adobe.otf");
        this.setTypeface(face);
    }
}
