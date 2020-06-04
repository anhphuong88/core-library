package com.app.core.ui.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.core.action.Action1;
import com.app.core.action.Action2;

public final class UILinearLayout {

    public static LinearLayout create(@NonNull Context context, Action1<LinearLayout> action) {
        LinearLayout layout = new LinearLayout(context);
        if (action != null) action.invoke(layout);
        return layout;
    }

    public static LinearLayout create(@NonNull ViewGroup parent, Action1<LinearLayout> action) {
        return create(parent.getContext(), action);
    }

    public static LinearLayout create(@NonNull RelativeLayout parent, Action2<LinearLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static LinearLayout create(@NonNull RelativeLayout parent, Action2<LinearLayout, RelativeLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static LinearLayout create(@NonNull LinearLayout parent, Action2<LinearLayout, LinearLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static LinearLayout create(@NonNull LinearLayout parent, Action2<LinearLayout, LinearLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static LinearLayout create(@NonNull FrameLayout parent, Action2<LinearLayout, FrameLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static LinearLayout create(@NonNull FrameLayout parent, Action2<LinearLayout, FrameLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static LinearLayout add(@NonNull ViewGroup parent, Action1<LinearLayout> action) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        if (action != null) action.invoke(layout);
        parent.addView(layout);
        return layout;
    }

    public static LinearLayout add(@NonNull RelativeLayout parent, Action2<LinearLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static LinearLayout add(@NonNull RelativeLayout parent, Action2<LinearLayout, RelativeLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static LinearLayout add(@NonNull LinearLayout parent, Action2<LinearLayout, LinearLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static LinearLayout add(@NonNull LinearLayout parent, Action2<LinearLayout, LinearLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static LinearLayout add(@NonNull FrameLayout parent, Action2<LinearLayout, FrameLayout.LayoutParams> action, int width, int height) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static LinearLayout add(@NonNull FrameLayout parent, Action2<LinearLayout, FrameLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }
}
