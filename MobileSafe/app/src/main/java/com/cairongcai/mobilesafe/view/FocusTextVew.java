package com.cairongcai.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HY-IT on 2017/5/11.
 */

public class FocusTextVew extends TextView {
    public FocusTextVew(Context context) {
        super(context);
    }

    public FocusTextVew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextVew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
