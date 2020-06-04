package com.app.core.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.core.action.Action1;
import com.app.core.action.Action2;
import com.app.core.ui.layout.UILayout;

public final class UITextView extends UITextStyle{
    /* For TexView Construct*/
    public static TextView create(@NonNull Context context, Action1<TextView> action) {
        TextView text = new TextView(context);
        if (action != null) action.invoke(text);
        return text;
    }

    public static TextView create(@NonNull ViewGroup parent, Action1<TextView> action) {
        return create(parent.getContext(), action);
    }

    public static TextView create(@NonNull RelativeLayout parent, Action2<TextView, RelativeLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static TextView create(@NonNull RelativeLayout parent, Action2<TextView, RelativeLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static TextView create(@NonNull LinearLayout parent, Action2<TextView, LinearLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static TextView create(@NonNull LinearLayout parent, Action2<TextView, LinearLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static TextView create(@NonNull FrameLayout parent, Action2<TextView, FrameLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static TextView create(@NonNull FrameLayout parent, Action2<TextView, FrameLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static TextView add(@NonNull ViewGroup parent, Action1<TextView> action) {
        TextView text = new TextView(parent.getContext());
        if (action != null) action.invoke(text);
        parent.addView(text);
        return text;
    }

    public static TextView add(@NonNull RelativeLayout parent, Action2<TextView, RelativeLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static TextView add(@NonNull RelativeLayout parent, Action2<TextView, RelativeLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static TextView add(@NonNull LinearLayout parent, Action2<TextView, LinearLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static TextView add(@NonNull LinearLayout parent, Action2<TextView, LinearLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static TextView add(@NonNull FrameLayout parent, Action2<TextView, FrameLayout.LayoutParams> action, int width, int height) {
        TextView text = new TextView(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static TextView add(@NonNull FrameLayout parent, Action2<TextView, FrameLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    /* For EditText construct*/
    public static EditText createEdit(@NonNull Context context, Action1<EditText> action) {
        EditText text = new EditText(context);
        if (action != null) action.invoke(text);
        return text;
    }

    public static EditText createEdit(@NonNull ViewGroup parent, Action1<EditText> action) {
        return createEdit(parent.getContext(), action);
    }

    public static EditText createEdit(@NonNull RelativeLayout parent, Action2<EditText, RelativeLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static EditText createEdit(@NonNull RelativeLayout parent, Action2<EditText, RelativeLayout.LayoutParams> action) {
        return createEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static EditText createEdit(@NonNull LinearLayout parent, Action2<EditText, LinearLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static EditText createEdit(@NonNull LinearLayout parent, Action2<EditText, LinearLayout.LayoutParams> action) {
        return createEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static EditText createEdit(@NonNull FrameLayout parent, Action2<EditText, FrameLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        return text;
    }

    public static EditText createEdit(@NonNull FrameLayout parent, Action2<EditText, FrameLayout.LayoutParams> action) {
        return createEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static EditText addEdit(@NonNull ViewGroup parent, Action1<EditText> action) {
        return createEdit(parent.getContext(), action);
    }

    public static EditText addEdit(@NonNull RelativeLayout parent, Action2<EditText, RelativeLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static EditText addEdit(@NonNull RelativeLayout parent, Action2<EditText, RelativeLayout.LayoutParams> action) {
        return addEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static EditText addEdit(@NonNull LinearLayout parent, Action2<EditText, LinearLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static EditText addEdit(@NonNull LinearLayout parent, Action2<EditText, LinearLayout.LayoutParams> action) {
        return addEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static EditText addEdit(@NonNull FrameLayout parent, Action2<EditText, FrameLayout.LayoutParams> action, int width, int height) {
        EditText text = new EditText(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        text.setLayoutParams(params);
        if (action != null) action.invoke(text, params);
        parent.addView(text);
        return text;
    }

    public static EditText addEdit(@NonNull FrameLayout parent, Action2<EditText, FrameLayout.LayoutParams> action) {
        return addEdit(parent, action, UILayout.Wrap, UILayout.Wrap);
    }
}
