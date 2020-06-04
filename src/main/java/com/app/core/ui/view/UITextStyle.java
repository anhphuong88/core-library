package com.app.core.ui.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;

import com.app.core.action.Action;
import com.app.core.util.StringUtil;
import com.app.core.util.UIUtil;

import java.util.List;

class UITextStyle {

    public static void font(@NonNull TextView view, String fontPath) {
        view.setTypeface(UIContext.font(view.getContext(), fontPath));
    }

    public static void styleSingle(@NonNull TextView view, int size, @ColorRes int rColor, String fontPath) {
        textSize(view, size);
        resTextColor(view, rColor);
        view.setMaxLines(1);
        view.setLines(1);
        view.setHorizontalScrollBarEnabled(false);
        view.setHorizontalFadingEdgeEnabled(false);
        view.setHorizontallyScrolling(false);
        view.setTypeface(UIContext.font(view.getContext(), fontPath));
        view.setEllipsize(TextUtils.TruncateAt.END);
    }

    public static void styleMultiple(@NonNull TextView view, int size, @ColorRes int rColor, String fontPath) {
        styleMultiple(view, size, rColor, fontPath, 0);
    }

    public static void styleMultiple(@NonNull TextView view, int size, @ColorRes int rColor, String fontPath, int startLine) {
        textSize(view, size);
        resTextColor(view, rColor);
        if (startLine > 1) view.setLines(startLine);
        view.setTypeface(UIContext.font(view.getContext(), fontPath));
        view.setEllipsize(TextUtils.TruncateAt.END);
    }

    public static void styleMultiple(@NonNull TextView view, int size, @ColorRes int rColor, String fontPath, int startLine, int maxLine) {
        textSize(view, size);
        resTextColor(view, rColor);
        if (startLine > 1) view.setLines(startLine);
        view.setMaxLines(maxLine);
        view.setTypeface(UIContext.font(view.getContext(), fontPath));
        view.setEllipsize(TextUtils.TruncateAt.END);
    }

    public static void underLine(@NonNull TextView view) {
        view.setPaintFlags(view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * @param view  TextView
     * @param value need to bring the original float value to multiply with 0.025
     */
    public static void spacing(@NonNull TextView view, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setLetterSpacing(value * 0.025f);
        }
    }

    public static void spacing(@NonNull TextView view, @DimenRes int rDimenFloat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setLetterSpacing(UIContext.dimenFloat(view.getContext(), rDimenFloat));
        }
    }

    public static void textSize(@NonNull TextView view, int size) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public static float textSize(@NonNull TextView view) {
        return view.getTextSize();
    }

    public static String text(@NonNull TextView view) {
        return view.getText() != null ? view.getText().toString() : "";
    }

    public static void text(@NonNull TextView view, @StringRes int rsText) {
        view.setText(UIView.resText(view, rsText));
    }

    public static void resTextColor(@NonNull TextView view, @ColorRes int rColor) {
        view.setTextColor(UIView.resColor(view, rColor));
    }

    public static void resTextHintColor(@NonNull TextView view, @ColorRes int rColor) {
        view.setHintTextColor(UIView.resColor(view, rColor));
    }

    public static void resText(@NonNull TextView view, @StringRes int rsText, @ColorRes int rColor) {
        view.setText(UIView.resText(view, rsText));
        resTextColor(view, rColor);
    }

    public static void resTextHint(@NonNull TextView view, @StringRes int rsHint, @ColorRes int rColor) {
        view.setHint(UIView.resText(view, rsHint));
        resTextHintColor(view, rColor);
    }

    /* Advanced Text */
    public static void highLight(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        highLight(text, range[0], range[1], fontPath, size, rColor);
    }

    public static void highLightMultiple(@NonNull TextView text, @NonNull List<String> inList, @NonNull String fontPath, int size, @ColorRes int rColor) {
//        String textStr = text(text).toLowerCase();
//        String inStr = in.toLowerCase();
//        int[] range = StringUtil.range(textStr, inStr);
//        highLight(text, range[0], range[1], fontPath, size, rColor);

        String textStr = text(text).toLowerCase();
        SpannableString span = new SpannableString(text(text));
        Typeface typeFont = UIContext.font(text.getContext(), fontPath);
        for (String in : inList) {
            String inStr = in.toLowerCase();
            String[] strings = inStr.trim().split("\\s+");
            float startSize = textSize(text);
            int[] range;
            for (String str : strings) {
                range = StringUtil.range(textStr, str);
                while (range[0] >= 0 && range[1] <= text(text).length()) {
                    span.setSpan(new RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    range = StringUtil.range(textStr, str, range[1]);
                }
            }
        }
        text.setText(span, TextView.BufferType.SPANNABLE);
    }

    public static void highLightMultipleAnClick(@NonNull TextView text, @NonNull List<String> inList, @NonNull List<Action> inListAction, @NonNull String fontPath, int size, @ColorRes int rColor) {
//        String textStr = text(text).toLowerCase();
//        String inStr = in.toLowerCase();
//        int[] range = StringUtil.range(textStr, inStr);
//        highLight(text, range[0], range[1], fontPath, size, rColor);

        String textStr = text(text).toLowerCase();
        SpannableString span = new SpannableString(text(text));
        Typeface typeFont = UIContext.font(text.getContext(), fontPath);
        int i = 0;
        for (String in : inList) {
            String inStr = in.toLowerCase();
            String[] strings = inStr.trim().split("\\s+");
            float startSize = textSize(text);
            int[] range;
            for (String str : strings) {
                range = StringUtil.range(textStr, str);
                while (range[0] >= 0 && range[1] <= text(text).length()) {
                    span.setSpan(new RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    if (inListAction.size() >= i && inListAction.get(i) != null) {
                        int finalI = i;
                        span.setSpan(new UIClickableSpan(() -> {
                            inListAction.get(finalI).invoke();
                            return null;
                        }), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                    span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    range = StringUtil.range(textStr, str, range[1]);
                }
            }
            i++;
        }
        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(span, TextView.BufferType.SPANNABLE);
    }

    public static void highLight(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        highLight(text, range[0], range[1], fontPath, size, rTextColor, rBGColor);
    }

    public static void highLightAndClick(@NonNull TextView text, @NonNull Action action, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        highLightAndClick(text, action, range[0], range[1], fontPath, size, rColor);
    }

    public static void highLightAndClick(@NonNull TextView text, @NonNull Action action, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        highLightAndClick(text, action, range[0], range[1], fontPath, size, rTextColor, rBGColor);
    }

    public static void highLightLast(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.rangeLast(textStr, inStr);
        highLight(text, range[0], range[1], fontPath, size, rColor);
    }

    public static void highLightLast(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.rangeLast(textStr, inStr);
        highLight(text, range[0], range[1], fontPath, size, rTextColor, rBGColor);
    }

    public static void highLightAll(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        if (range[0] >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            while (range[0] >= 0 && range[1] <= text(text).length()) {
                span.setSpan(new RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                range = StringUtil.range(textStr, inStr, range[1]);
            }
            text.setText(span, TextView.BufferType.NORMAL);
        }
    }

    public static void highLightAll(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        int[] range = StringUtil.range(textStr, inStr);
        if (range[0] >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            while (range[0] >= 0 && range[1] <= text(text).length()) {
                span.setSpan(new RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UIRoundedBackgroundSpan(UIContext.colorInt(text.getContext(), rBGColor), UIContext.colorInt(text.getContext(), rTextColor), 0f), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                range = StringUtil.range(textStr, inStr, range[1]);
            }
            text.setText(span, TextView.BufferType.NORMAL);
        }
    }

    public static void highLightSentenceAll(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        String[] strings = inStr.trim().split("\\s+");
        Typeface typeFont = UIContext.font(text.getContext(), fontPath);
        float startSize = textSize(text);
        SpannableString span = new SpannableString(text(text));
        int[] range;
        for (String str : strings) {
            range = StringUtil.range(textStr, str);
            while (range[0] >= 0 && range[1] <= text(text).length()) {
                span.setSpan(new RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                range = StringUtil.range(textStr, str, range[1]);
            }
        }
        text.setText(span, TextView.BufferType.SPANNABLE);
    }

    public static void highLightSentenceAll(@NonNull TextView text, @NonNull String in, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        String textStr = text(text).toLowerCase();
        String inStr = in.toLowerCase();
        String[] strings = inStr.trim().split("\\s+");
        Typeface typeFont = UIContext.font(text.getContext(), fontPath);
        float startSize = textSize(text);
        SpannableString span = new SpannableString(text(text));
        int[] range;
        for (String str : strings) {
            range = StringUtil.range(textStr, str);
            while (range[0] >= 0 && range[1] <= text(text).length()) {
                span.setSpan(new RelativeSizeSpan((float) (size / startSize)), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UITypefaceSpan(typeFont), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                span.setSpan(new UIRoundedBackgroundSpan(UIContext.colorInt(text.getContext(), rBGColor), UIContext.colorInt(text.getContext(), rTextColor), 0f), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                range = StringUtil.range(textStr, str, range[1]);
            }
        }
        text.setText(span, TextView.BufferType.SPANNABLE);
    }

    public static void highLightAndClick(@NonNull TextView text, @NonNull Action action, int from, int to, @NonNull String fontPath, int size, @ColorRes int rColor) {
        if (from >= 0 && to >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            span.setSpan(new RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UITypefaceSpan(typeFont), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UIClickableSpan(() -> {
                action.invoke();
                return null;
            }), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            text.setText(span, TextView.BufferType.NORMAL);
            text.setMovementMethod(LinkMovementMethod.getInstance());
            text.setHighlightColor(Color.TRANSPARENT);
        }
    }

    public static void highLightAndClick(@NonNull TextView text, @NonNull Action action, int from, int to, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        if (from >= 0 && to >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            span.setSpan(new RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UITypefaceSpan(typeFont), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UIClickableSpan(() -> {
                action.invoke();
                return null;
            }), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UIRoundedBackgroundSpan(UIContext.colorInt(text.getContext(), rBGColor), UIContext.colorInt(text.getContext(), rTextColor), 0f), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            text.setText(span, TextView.BufferType.NORMAL);
            text.setMovementMethod(LinkMovementMethod.getInstance());
            text.setHighlightColor(Color.TRANSPARENT);
        }
    }

    public static void highLight(@NonNull TextView text, int from, int to, @NonNull String fontPath, int size, @ColorRes int rColor) {
        if (from >= 0 && to >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            span.setSpan(new RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UITypefaceSpan(typeFont), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new ForegroundColorSpan(UIView.resColor(text, rColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            text.setText(span, TextView.BufferType.NORMAL);
        }
    }

    public static void highLight(@NonNull TextView text, int from, int to, @NonNull String fontPath, int size, @ColorRes int rTextColor, @ColorRes int rBGColor) {
        if (from >= 0 && to >= 0) {
            Typeface typeFont = UIContext.font(text.getContext(), fontPath);
            float startSize = textSize(text);
            SpannableString span = new SpannableString(text(text));
            span.setSpan(new RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UITypefaceSpan(typeFont), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span.setSpan(new UIRoundedBackgroundSpan(UIContext.colorInt(text.getContext(), rBGColor), UIContext.colorInt(text.getContext(), rTextColor), 0f), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            text.setText(span, TextView.BufferType.NORMAL);
        }
    }


    @MainThread
    public static void setCursorColor(@NonNull EditText view, @ColorRes int rColor) {
        UIUtil.setCursorColor(view, rColor);
    }
}
