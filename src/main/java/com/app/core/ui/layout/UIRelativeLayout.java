package com.app.core.ui.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.core.action.Action1;
import com.app.core.action.Action2;

public final class UIRelativeLayout {

    public static RelativeLayout create(@NonNull Context context, Action1<RelativeLayout> action) {
        RelativeLayout layout = new RelativeLayout(context);
        if (action != null) action.invoke(layout);
        return layout;
    }

    public static RelativeLayout create(@NonNull ViewGroup parent, Action1<RelativeLayout> action) {
        return create(parent.getContext(), action);
    }

    public static RelativeLayout create(@NonNull RelativeLayout parent, Action2<RelativeLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static RelativeLayout create(@NonNull RelativeLayout parent, Action2<RelativeLayout, RelativeLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static RelativeLayout create(@NonNull LinearLayout parent, Action2<RelativeLayout, LinearLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static RelativeLayout create(@NonNull LinearLayout parent, Action2<RelativeLayout, LinearLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static RelativeLayout create(@NonNull FrameLayout parent, Action2<RelativeLayout, FrameLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        return layout;
    }

    public static RelativeLayout create(@NonNull FrameLayout parent, Action2<RelativeLayout, FrameLayout.LayoutParams> action) {
        return create(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static RelativeLayout add(@NonNull ViewGroup parent, Action1<RelativeLayout> action) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        if (action != null) action.invoke(layout);
        parent.addView(layout);
        return layout;
    }

    public static RelativeLayout add(@NonNull RelativeLayout parent, Action2<RelativeLayout, RelativeLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        RelativeLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static RelativeLayout add(@NonNull RelativeLayout parent, Action2<RelativeLayout, RelativeLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static RelativeLayout add(@NonNull LinearLayout parent, Action2<RelativeLayout, LinearLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        LinearLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static RelativeLayout add(@NonNull LinearLayout parent, Action2<RelativeLayout, LinearLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static RelativeLayout add(@NonNull FrameLayout parent, Action2<RelativeLayout, FrameLayout.LayoutParams> action, int width, int height) {
        RelativeLayout layout = new RelativeLayout(parent.getContext());
        FrameLayout.LayoutParams params = UILayout.wrap(parent, width, height);
        layout.setLayoutParams(params);
        if (action != null) action.invoke(layout, params);
        parent.addView(layout);
        return layout;
    }

    public static RelativeLayout add(@NonNull FrameLayout parent, Action2<RelativeLayout, FrameLayout.LayoutParams> action) {
        return add(parent, action, UILayout.Wrap, UILayout.Wrap);
    }

    public static void center(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        }
    }

    public static void centerHorizontal(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        }
    }

    public static void centerVertical(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        }
    }

    public static void centerVerticalLeft(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
        }
    }

    public static void centerVerticalRight(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        }
    }

    public static void left(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
        }
    }

    public static void right(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        }
    }

    public static void top(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
        }
    }

    public static void bottom(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
        }
    }

    public static void leftTop(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
        }
    }

    public static void leftBottom(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
        }
    }

    public static void rightTop(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
        }
    }

    public static void rightBottom(@NonNull View view) {
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
        }
    }
}
