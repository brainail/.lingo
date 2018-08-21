package org.brainail.flysearch;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.core.graphics.drawable.DrawableCompat;

class ViewUtils {
    private static final int[] TEMP_ARRAY = new int[1];
    
    public static void showSoftKeyboardDelayed(final EditText editText, long delay) {
        editText.postDelayed(() -> {
            final InputMethodManager inputMethodManager
                    = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != inputMethodManager) {
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, delay);
    }
    
    public static void closeSoftKeyboard(Activity activity) {
        View currentFocusView = activity.getCurrentFocus();
        if (currentFocusView != null) {
            final InputMethodManager inputMethodManager
                    = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != inputMethodManager) {
                inputMethodManager.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
            }
        }
    }
    
    public static void closeSoftKeyboard(final View view) {
        final InputMethodManager inputMethodManager
                = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != inputMethodManager) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public static int dpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }
    
    public static int pxToDp(int px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (px / metrics.density);
    }
    
    public static int getThemeAttrColor(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }
    
    public static Drawable getTinted(Drawable icon, @ColorInt int color) {
        if (icon == null) {
            return null;
        }
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, color);
        return icon;
    }
    
}
