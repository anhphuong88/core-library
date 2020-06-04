package com.app.core.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.ViewGroup;

public final class UIContext {

    public static Typeface font(@NonNull Context context, @NonNull String fontPath) {
        return UIFont.INSTANCE.load(context.getAssets(), fontPath);
    }

    private static DisplayMetrics displayMetrics(@NonNull Context context) {
        return resources(context).getDisplayMetrics();
    }

    public static Resources resources(@NonNull Context context) {
        return context.getResources();
    }

    public static float density(@NonNull Context context) {
        return resources(context).getDisplayMetrics().density;
    }

    public static int dpToPx(@NonNull Context context, float dp) {
        return Math.round(dp * displayMetrics(context).density);
    }

    public static int pxToDp(@NonNull Context context, float px) {
        return Math.round(px / displayMetrics(context).density);
    }

    public static int spToPx(@NonNull Context context, float dp) {
        return Math.round(dp * displayMetrics(context).scaledDensity);
    }

    public static int pxToSp(@NonNull Context context, float px) {
        return Math.round(px / displayMetrics(context).scaledDensity);
    }

    public static float dimenFloat(@NonNull Context context, int dimenFloat) {
        TypedValue outValue = new TypedValue();
        resources(context).getValue(dimenFloat, outValue, true);
        return outValue.getFloat();
    }

    public static int dimen(@NonNull Context context, int rDimen) {
        return resources(context).getDimensionPixelSize(rDimen);
    }

    public static int screenWidth(@NonNull Context context) {
        return displayMetrics(context).widthPixels;
    }

    public static int screenHeight(@NonNull Context context) {
        return displayMetrics(context).heightPixels;
    }

    public static int statusBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = resources(context).getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources(context).getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static ViewGroup.LayoutParams match() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams wrap() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams wrap(int w, int h) {
        return new ViewGroup.LayoutParams(w, h);
    }

    public static Drawable drawable(@NonNull Context context, @DrawableRes int rDrawable) {
        return AppCompatResources.getDrawable(context, rDrawable);
    }

    public static Drawable transparent(@NonNull Context context) {
        return new ColorDrawable(Color.TRANSPARENT);
    }

    public static Drawable colorDrawable(@NonNull Context context, @ColorRes int rColor) {
        return new ColorDrawable(resColor(context, rColor));
    }

    public static int resColor(@NonNull Context context, @ColorRes int rColor) {
        return ContextCompat.getColor(context, rColor);
    }

    @ColorInt
    public static int colorInt(@NonNull Context context, @ColorRes int rColor) {
        return ResourcesCompat.getColor(context.getResources(), rColor, null);
    }

    public static String resText(@NonNull Context context, @StringRes int resText) {
        CharSequence sequence = resources(context).getText(resText);
        return sequence.toString();
    }

    public static Drawable circle(@NonNull Context context, @ColorRes int rColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(resColor(context, rColor));
        return shape;
    }

    public static Drawable circle(@NonNull Context context, int border, @ColorRes int rColor, @ColorRes int rBorderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(resColor(context, rColor));
        shape.setStroke(border, resColor(context, rBorderColor));
        return shape;
    }

    public static Drawable circleGradient(@NonNull Context context, @ColorRes int startColor, @ColorRes int endColor) {
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{UIContext.colorInt(context, startColor), UIContext.colorInt(context, endColor)});
        shape.setShape(GradientDrawable.OVAL);
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setGradientRadius(90f);
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static StateListDrawable circle(@NonNull Context context, int border, @ColorRes int rColor, @ColorRes int rBorderColor, @ColorRes int rSelectedBorderColor) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{android.R.attr.state_pressed}, circle(context, border, rColor, rSelectedBorderColor));
        out.addState(StateSet.WILD_CARD, circle(context, border, rColor, rBorderColor));
        return out;
    }

    public static Drawable squareGradient(@NonNull Context context, int radius, @ColorRes int rStartColor, @ColorRes int rEndColor) {
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
//                new int[]{0xFF1489E2, 0xFF1BA2D2});
                new int[]{UIContext.colorInt(context, rStartColor), UIContext.colorInt(context, rEndColor)});
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setGradientRadius(90f);
        shape.setCornerRadius(radius);
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static Drawable squareGradient(@NonNull Context context, int radius, @ColorRes int rStartColor, @ColorRes int rEndColor, GradientDrawable.Orientation orientation) {
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
//                new int[]{0xFF1489E2, 0xFF1BA2D2});
                new int[]{UIContext.colorInt(context, rStartColor), UIContext.colorInt(context, rEndColor)});
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setGradientRadius(90f);
        shape.setCornerRadius(radius);
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static Drawable squareGradient(Context context, int border, int rStartColor, int rEndColor, int rBorderColor) {
        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
//                new int[]{0xFF1489E2, 0xFF1BA2D2});
                new int[]{UIContext.colorInt(context, rStartColor), UIContext.colorInt(context, rEndColor)});
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setGradientRadius(0f);
        shape.setStroke(border, UIContext.resColor(context, rBorderColor));
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static Drawable square(@NonNull Context context, int radius, @ColorRes int rColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(resColor(context, rColor));
        shape.setCornerRadius(radius);
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static Drawable square(@NonNull Context context, int radius, int border, @ColorRes int rColor, @ColorRes int rBorderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(resColor(context, rColor));
        shape.setCornerRadius(radius);
        shape.setStroke(border, resColor(context, rBorderColor));
        shape.setBounds(0, 0, 0, 0);
        return shape;
    }

    public static StateListDrawable square(@NonNull Context context, int radius, int border, @ColorRes int rColor, @ColorRes int rBorderColor, @ColorRes int rSelectedBorderColor) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{android.R.attr.state_pressed}, square(context, radius, border, rColor, rSelectedBorderColor));
        out.addState(StateSet.WILD_CARD, square(context, radius, border, rColor, rBorderColor));
        return out;
    }

    public static Bitmap circleImage(@NonNull Context context, Bitmap bitmap, int radius, @ColorRes int rColor) {
        Bitmap result;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
            result = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        } else {
            result = bitmap;
        }
        Bitmap out = Bitmap.createBitmap(result.getWidth(), result.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(out);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, result.getWidth(), result.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(resColor(context, rColor));
        canvas.drawCircle(result.getWidth() / 2 + 0.7f, result.getHeight() / 2 + 0.7f
                , result.getHeight() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode((PorterDuff.Mode.SRC_IN)));
        canvas.drawBitmap(result, rect, rect, paint);

        return out;
    }

    public static Drawable circleWithText(@NonNull Context context, int size, @ColorRes int rCircleColor, String text, int sizeText, @ColorRes int rColorText, @NonNull Typeface typeface) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(resColor(context, android.R.color.transparent));

        Paint pCircle = new Paint();
        pCircle.setStyle(Paint.Style.FILL);
        pCircle.setColor(resColor(context, rCircleColor));
        pCircle.setAntiAlias(true);

        int radius = Math.min(canvas.getWidth(), canvas.getHeight() / 2);
        int pad = 0;
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius - pad, pCircle);

        Paint pText = new Paint(Paint.ANTI_ALIAS_FLAG);
        pText.setTypeface(typeface);
        pText.setColor(resColor(context, rColorText));
        pText.setTextSize(sizeText);

        Rect bound = new Rect();
        pText.getTextBounds(text, 0, text.length(), bound);
        float x = (bitmap.getWidth() - bound.width()) / 2;
        float y = (bitmap.getHeight() + bound.height()) / 2;

        canvas.drawText(text, x, y, pText);

        return new BitmapDrawable(resources(context), bitmap);
    }

    public static Drawable line(@NonNull Context context, int width, int height, int lWidthInPixel, @ColorRes int rColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas((bitmap));
        canvas.drawColor(resColor(context, android.R.color.transparent));

        Paint pLine = new Paint();
        pLine.setStyle(Paint.Style.STROKE);
        pLine.setStrokeWidth(lWidthInPixel);
        pLine.setColor(resColor(context, rColor));
        pLine.setAntiAlias(false);

        canvas.drawLine(0, 0, width, 0, pLine);

        return new BitmapDrawable(resources(context), bitmap);
    }

    public static Drawable squareDualSide(@NonNull Context context, int width, int height, int borderWidthInPixel, @ColorRes int rColor, @ColorRes int rColorBorder) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(resColor(context, rColor));

        Paint pLine = new Paint();
        pLine.setStyle(Paint.Style.STROKE);
        pLine.setStrokeWidth(borderWidthInPixel);
        pLine.setColor(resColor(context, rColorBorder));
        pLine.setAntiAlias(false);

        canvas.drawLine(0, 0, 0, height, pLine);
        canvas.drawLine(width - borderWidthInPixel, 0, width - borderWidthInPixel, height, pLine);

        return new BitmapDrawable(resources(context), bitmap);
    }

}
