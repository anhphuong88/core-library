package com.app.core.ui.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.core.action.Action1;
import com.app.core.action.Action2;

public final class UIFrameLayout {

    public static FrameLayout create(@NonNull Context context, Action1<FrameLayout> action) {
        FrameLayout layout = new FrameLayout(context);
        if (action != null) action.invoke(layout);
        return layout;
    }

    public static FrameLayout create(@NonNull ViewGroup parent, Action1<FrameLayout> action) {
        return create(parent.getContext(), action);
    }

    public static FrameLayout create(@NonNull RelativeLayout parent, Action2<FrameLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static FrameLayout create(@NonNull RelativeLayout parent, Action2<FrameLayout, RelativeLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static FrameLayout create(@NonNull LinearLayout parent, Action2<FrameLayout, LinearLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static FrameLayout create(@NonNull LinearLayout parent, Action2<FrameLayout, LinearLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static FrameLayout create(@NonNull FrameLayout parent, Action2<FrameLayout, FrameLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static FrameLayout create(@NonNull FrameLayout parent, Action2<FrameLayout, FrameLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static FrameLayout add(@NonNull ViewGroup parent, Action1<FrameLayout> action) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        if (action != null) action.invoke(layout);
        parent.addView(layout);
        return layout;
    }

    public static FrameLayout add(@NonNull RelativeLayout parent, Action2<FrameLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static FrameLayout add(@NonNull RelativeLayout parent, Action2<FrameLayout, RelativeLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static FrameLayout add(@NonNull LinearLayout parent, Action2<FrameLayout, LinearLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static FrameLayout add(@NonNull LinearLayout parent, Action2<FrameLayout, LinearLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static FrameLayout add(@NonNull FrameLayout parent, Action2<FrameLayout, FrameLayout.LayoutParams> action, int width, int height) {
        FrameLayout layout = new FrameLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static FrameLayout add(@NonNull FrameLayout parent, Action2<FrameLayout, FrameLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }
}
