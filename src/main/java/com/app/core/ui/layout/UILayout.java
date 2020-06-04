package com.app.core.ui.layout;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public final class UILayout {

    public static final int Match = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int Wrap = ViewGroup.LayoutParams.WRAP_CONTENT;

    /* Match Parent */
    public static RelativeLayout.LayoutParams match(RelativeLayout parent) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams match(LinearLayout parent) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams match(FrameLayout parent) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams match(ViewGroup parent) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /* Wrap Content */
    public static RelativeLayout.LayoutParams wrap(RelativeLayout parent) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams wrap(LinearLayout parent) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams wrap(FrameLayout parent) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams wrap(ViewGroup parent) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /* Match H & W*/
    public static RelativeLayout.LayoutParams matchH(RelativeLayout parent, int w) {
        return new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams matchH(LinearLayout parent, int w) {
        return new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams matchH(FrameLayout parent, int w) {
        return new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams matchH(ViewGroup parent, int w) {
        return new ViewGroup.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams matchW(RelativeLayout parent, int h) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static LinearLayout.LayoutParams matchW(LinearLayout parent, int h) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static FrameLayout.LayoutParams matchW(FrameLayout parent, int h) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static ViewGroup.LayoutParams matchW(ViewGroup parent, int h) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, h);
    }

    /* Wrap H & W*/
    public static RelativeLayout.LayoutParams wrapH(RelativeLayout parent, int w) {
        return new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams wrapH(LinearLayout parent, int w) {
        return new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams wrapH(FrameLayout parent, int w) {
        return new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams wrapH(ViewGroup parent, int w) {
        return new ViewGroup.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams wrapW(RelativeLayout parent, int h) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static LinearLayout.LayoutParams wrapW(LinearLayout parent, int h) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static FrameLayout.LayoutParams wrapW(FrameLayout parent, int h) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, h);
    }

    public static ViewGroup.LayoutParams wrapW(ViewGroup parent, int h) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, h);
    }

    /* wrap W match H*/
    public static RelativeLayout.LayoutParams wrapMatch(RelativeLayout parent) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams wrapMatch(LinearLayout parent) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams wrapMatch(FrameLayout parent) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams wrapMatch(ViewGroup parent) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /* match W wrap H*/
    public static RelativeLayout.LayoutParams matchWrap(RelativeLayout parent) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams matchWrap(LinearLayout parent) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams matchWrap(FrameLayout parent) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams matchWrap(ViewGroup parent) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /* define W H*/
    public static RelativeLayout.LayoutParams wrap(RelativeLayout parent, int w, int h) {
        return new RelativeLayout.LayoutParams(w, h);
    }

    public static LinearLayout.LayoutParams wrap(LinearLayout parent, int w, int h) {
        return new LinearLayout.LayoutParams(w, h);
    }

    public static FrameLayout.LayoutParams wrap(FrameLayout parent, int w, int h) {
        return new FrameLayout.LayoutParams(w, h);
    }

    public static ViewGroup.LayoutParams wrap(ViewGroup parent, int w, int h) {
        return new ViewGroup.LayoutParams(w, h);
    }
}
