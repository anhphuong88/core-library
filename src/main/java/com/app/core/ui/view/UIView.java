package com.app.core.ui.view;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.core.action.Action1;
import com.app.core.action.Action2;
import com.app.core.action.Func;
import com.app.core.ui.layout.UILayout;

public final class UIView extends UIViewLocation {

    public static final int Match = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int Wrap = ViewGroup.LayoutParams.WRAP_CONTENT;

    public static <T extends View> T create(@NonNull ViewGroup parent, T view, Action1<T> action) {
        if (action != null) action.invoke(view);
        return view;
    }

    public static <T extends View> T create(@NonNull RelativeLayout parent, T view, Action2<T, RelativeLayout.LayoutParams> action, int width, int height) {
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        return view;
    }

    public static <T extends View> T create(@NonNull LinearLayout parent, T view, Action2<T, LinearLayout.LayoutParams> action, int width, int height) {
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        return view;
    }

    public static <T extends View> T create(@NonNull FrameLayout parent, T view, Action2<T, FrameLayout.LayoutParams> action, int width, int height) {
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        return view;
    }

    public static <T extends View> T add(@NonNull ViewGroup parent, T view, Action1<T> action) {
        if (action != null) action.invoke(view);
        parent.addView(view);
        return view;
    }

    public static <T extends View> T add(@NonNull RelativeLayout parent, T view, Action2<T, RelativeLayout.LayoutParams> action, int width, int height) {
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        parent.addView(view);
        return view;
    }

    public static <T extends View> T add(@NonNull LinearLayout parent, T view, Action2<T, LinearLayout.LayoutParams> action, int width, int height) {
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        parent.addView(view);
        return view;
    }

    public static <T extends View> T add(@NonNull FrameLayout parent, T view, Action2<T, FrameLayout.LayoutParams> action, int width, int height) {
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        view.setLayoutParams(params);
        if (action != null) action.invoke(view, params);
        parent.addView(view);
        return view;
    }

    public static void backgroundColor(@NonNull View view, @ColorRes int rColor) {
        view.setBackgroundColor(resColor(view, rColor));
    }

    public static void backgroundTransparent(@NonNull View view) {
        view.setBackgroundColor(resColor(view, android.R.color.transparent));
    }
    @SuppressWarnings("deprecation")
    public static void background(@NonNull View view, @DrawableRes int rDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(UIContext.drawable(view.getContext(), rDrawable));
        } else {
            view.setBackgroundDrawable(UIContext.drawable(view.getContext(), rDrawable));
        }
    }
    @SuppressWarnings("deprecation")
    public static void background(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
    @SuppressWarnings("deprecation")
    public static void background(@NonNull View view, Func<Drawable> createDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(createDrawable.invoke());
        } else {
            view.setBackgroundDrawable(createDrawable.invoke());
        }
    }

    public static int dpToPx(@NonNull View view, float dp) {
        return UIContext.dpToPx(view.getContext(), dp);
    }

    public static int dimen(@NonNull View view, @DimenRes int rDimen) {
        return UIContext.dimen(view.getContext(), rDimen);
    }

    public static float dimenFloat(@NonNull View view, int dimenFloat) {
        return UIContext.dimenFloat(view.getContext(), dimenFloat);
    }

    public static void visible(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void invisible(@NonNull View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void gone(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    public static void toggleInvisible(@NonNull View view) {
        if (view.getVisibility() == View.VISIBLE) {
            invisible(view);
        } else {
            visible(view);
        }
    }

    public static void toggleGone(@NonNull View view) {
        if (view.getVisibility() == View.VISIBLE) {
            gone(view);
        } else {
            visible(view);
        }
    }

    public static Drawable drawable(@NonNull View view, int rDrawable) {
        return UIContext.drawable(view.getContext(), rDrawable);
    }

    public static int resColor(@NonNull View view, @ColorRes int rColor) {
        return UIContext.resColor(view.getContext(), rColor);
    }

    @ColorInt
    public static int colorInt(@NonNull View view, @ColorRes int rColor) {
        return UIContext.colorInt(view.getContext(), rColor);
    }

    public static String resText(@NonNull View view, int resText) {
        return UIContext.resText(view.getContext(), resText);
    }

    public static void padding(@NonNull View view, int ltrb) {
        view.setPadding(ltrb, ltrb, ltrb, ltrb);
    }

    public static void padding(@NonNull View view, int lr, int tb) {
        view.setPadding(lr, tb, lr, tb);
    }

    public static void padding(@NonNull View view, int l, int t, int r, int b) {
        view.setPadding(l, t, r, b);
    }

    public static void paddingLeft(@NonNull View view, int l) {
        view.setPadding(l, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void paddingTop(@NonNull View view, int t) {
        view.setPadding(view.getPaddingLeft(), t, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void paddingRight(@NonNull View view, int r) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), r, view.getPaddingBottom());
    }

    public static void paddingBottom(@NonNull View view, int b) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), b);
    }

    public static void padDimen(@NonNull View view, @DimenRes int rDimenLtrb) {
        view.setPadding(UIView.dimen(view, rDimenLtrb), UIView.dimen(view, rDimenLtrb),
                UIView.dimen(view, rDimenLtrb), UIView.dimen(view, rDimenLtrb));
    }

    public static void padDimen(@NonNull View view, @DimenRes int rDimenLr, @DimenRes int rDimenTb) {
        view.setPadding(UIView.dimen(view, rDimenLr), UIView.dimen(view, rDimenTb),
                UIView.dimen(view, rDimenLr), UIView.dimen(view, rDimenTb));
    }

    public static void padDimen(@NonNull View view, @DimenRes int rL, @DimenRes int rT, @DimenRes int rR, @DimenRes int rB) {
        view.setPadding(UIView.dimen(view, rL), UIView.dimen(view, rT),
                UIView.dimen(view, rR), UIView.dimen(view, rB));
    }

    public static void padDimenLeft(@NonNull View view, @DimenRes int rL) {
        view.setPadding(UIView.dimen(view, rL), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void padDimenTop(@NonNull View view, @DimenRes int rT) {
        view.setPadding(view.getPaddingLeft(), UIView.dimen(view, rT), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void padDimenRight(@NonNull View view, @DimenRes int rR) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), UIView.dimen(view, rR), view.getPaddingBottom());
    }

    public static void padDimenBottom(@NonNull View view, @DimenRes int rB) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), UIView.dimen(view, rB));
    }

    public static void margin(@NonNull View view, int l, int t, int r, int b) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(l, t, r, b);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(l, t, r, b);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(l, t, r, b);
        }
    }

    public static void margin(@NonNull View view, int lr, int tb) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(lr, tb, lr, tb);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(lr, tb, lr, tb);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(lr, tb, lr, tb);
        }
    }

    public static void margin(@NonNull View view, int ltrb) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(ltrb, ltrb, ltrb, ltrb);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(ltrb, ltrb, ltrb, ltrb);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(ltrb, ltrb, ltrb, ltrb);
        }
    }

    public static void marginLeft(@NonNull View view, int value) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(value, ((RelativeLayout.LayoutParams) v).topMargin,
                    ((RelativeLayout.LayoutParams) v).rightMargin,
                    ((RelativeLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(value, ((LinearLayout.LayoutParams) v).topMargin,
                    ((LinearLayout.LayoutParams) v).rightMargin,
                    ((LinearLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(value, ((FrameLayout.LayoutParams) v).topMargin,
                    ((FrameLayout.LayoutParams) v).rightMargin,
                    ((FrameLayout.LayoutParams) v).bottomMargin);
        }
    }

    public static void marginTop(@NonNull View view, int value) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(((RelativeLayout.LayoutParams) v).leftMargin, value,
                    ((RelativeLayout.LayoutParams) v).rightMargin,
                    ((RelativeLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(((LinearLayout.LayoutParams) v).leftMargin, value,
                    ((LinearLayout.LayoutParams) v).rightMargin,
                    ((LinearLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(((FrameLayout.LayoutParams) v).leftMargin, value,
                    ((FrameLayout.LayoutParams) v).rightMargin,
                    ((FrameLayout.LayoutParams) v).bottomMargin);
        }
    }

    public static void marginRight(@NonNull View view, int value) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(((RelativeLayout.LayoutParams) v).leftMargin,
                    ((RelativeLayout.LayoutParams) v).topMargin,
                    value,
                    ((RelativeLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(((LinearLayout.LayoutParams) v).leftMargin,
                    ((LinearLayout.LayoutParams) v).topMargin,
                    value,
                    ((LinearLayout.LayoutParams) v).bottomMargin);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(((FrameLayout.LayoutParams) v).leftMargin,
                    ((FrameLayout.LayoutParams) v).topMargin,
                    value,
                    ((FrameLayout.LayoutParams) v).bottomMargin);
        }
    }

    public static void marginBottom(@NonNull View view, int value) {
        ViewGroup.LayoutParams v = view.getLayoutParams();
        if (v instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) v).setMargins(((RelativeLayout.LayoutParams) v).leftMargin,
                    ((RelativeLayout.LayoutParams) v).topMargin,
                    ((RelativeLayout.LayoutParams) v).rightMargin,
                    value);
        } else if (v instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) v).setMargins(((LinearLayout.LayoutParams) v).leftMargin,
                    ((LinearLayout.LayoutParams) v).topMargin,
                    ((LinearLayout.LayoutParams) v).rightMargin,
                    value);
        } else if (v instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) v).setMargins(((FrameLayout.LayoutParams) v).leftMargin,
                    ((FrameLayout.LayoutParams) v).topMargin,
                    ((FrameLayout.LayoutParams) v).rightMargin,
                    value);
        }
    }

    public static void marginDimen(@NonNull View view, @DimenRes int rL, @DimenRes int rT, @DimenRes int rR, @DimenRes int rB) {
        margin(view, UIView.dimen(view, rL), UIView.dimen(view, rT),
                UIView.dimen(view, rR), UIView.dimen(view, rB));
    }

    public static void marginDimen(@NonNull View view, @DimenRes int rLr, @DimenRes int rTb) {
        margin(view, UIView.dimen(view, rLr), UIView.dimen(view, rTb));
    }

    public static void marginDimen(@NonNull View view, @DimenRes int rLtrb) {
        margin(view, UIView.dimen(view, rLtrb));
    }

    public static void marginDimenLeft(@NonNull View view, @DimenRes int rValue) {
        marginLeft(view, UIView.dimen(view, rValue));
    }

    public static void marginDimenTop(@NonNull View view, @DimenRes int rValue) {
        marginTop(view, UIView.dimen(view, rValue));
    }

    public static void marginDimenRight(@NonNull View view, @DimenRes int rValue) {
        marginRight(view, UIView.dimen(view, rValue));
    }

    public static void marginDimenBottom(@NonNull View view, @DimenRes int rValue) {
        marginBottom(view, UIView.dimen(view, rValue));
    }

    public static Drawable circle(@NonNull View view, @ColorRes int rColor) {
        return UIContext.circle(view.getContext(), rColor);
    }

    public static Drawable circle(@NonNull View view, int border, @ColorRes int rColor, @ColorRes int rBorderColor) {
        return UIContext.circle(view.getContext(), border, rColor, rBorderColor);
    }

    public static StateListDrawable circle(@NonNull View view, int border, @ColorRes int rColor, @ColorRes int rBorderColor, @ColorRes int rSelectedBorderColor) {
        return UIContext.circle(view.getContext(), border, rColor, rBorderColor, rSelectedBorderColor);
    }

    public static Drawable circleGradient(@NonNull View view, @ColorRes int rStartColor, @ColorRes int rEndColor) {
        return UIContext.circleGradient(view.getContext(), rStartColor, rEndColor);
    }

    public static Drawable square(@NonNull View view, int radius, @ColorRes int rColor) {
        return UIContext.square(view.getContext(), radius, rColor);
    }

    public static Drawable squareGradient(@NonNull View view, int radius, @ColorRes int rStartColor, @ColorRes int rEndColor) {
        return UIContext.squareGradient(view.getContext(), radius, rStartColor, rEndColor);
    }

    public static Drawable squareGradient(@NonNull View view, int radius, @ColorRes int rStartColor, @ColorRes int rEndColor, GradientDrawable.Orientation orientation) {
        return UIContext.squareGradient(view.getContext(), radius, rStartColor, rEndColor, orientation);
    }

    public static Drawable squareBorderGradient(@NonNull View view, int border, @ColorRes int rStartColor,
                                                @ColorRes int rEndColor, @ColorRes int rBorderColor) {
        return UIContext.squareGradient(view.getContext(), border, rStartColor, rEndColor, rBorderColor);
    }

    public static Drawable square(@NonNull View view, int radius, int border, @ColorRes int rColor, @ColorRes int rBorderColor) {
        return UIContext.square(view.getContext(), radius, border, rColor, rBorderColor);
    }

    public static StateListDrawable square(@NonNull View view, int radius, int border, @ColorRes int rColor, @ColorRes int rBorderColor, @ColorRes int rSelectedBorderColor) {
        return UIContext.square(view.getContext(), radius, border, rColor, rBorderColor, rSelectedBorderColor);
    }

    public static Bitmap circleImage(@NonNull View view, @NonNull Bitmap bitmap, int radius, @ColorRes int rColor) {
        return UIContext.circleImage(view.getContext(), bitmap, radius, rColor);
    }

    public static Drawable circleWithText(@NonNull View view, int size, @ColorRes int rCircleColor, String text, int sizeText, @ColorRes int rColorText, @NonNull Typeface typeface) {
        return UIContext.circleWithText(view.getContext(), size, rCircleColor, text, sizeText, rColorText, typeface);
    }

    public static Drawable squareDualSide(@NonNull View view, int borderWidthInPixel, @ColorRes int rColor, @ColorRes int rColorBorder) {
        return UIContext.squareDualSide(view.getContext(), UIContext.screenWidth(view.getContext()), UIContext.screenHeight(view.getContext()), borderWidthInPixel, rColor, rColorBorder);
    }
}
