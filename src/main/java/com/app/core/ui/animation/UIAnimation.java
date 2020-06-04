package com.app.core.ui.animation;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.app.core.action.Action1;
import com.app.core.thread.UIThread;

public final class UIAnimation {
    public static long animTime = 300L;
    public static long animLoadTime = 350L;
    public static long animLongTime = 600L;
    public static long animFastTime = 280L;
    public static long animWaitTime = 50L;

    private static final int type = Animation.RELATIVE_TO_PARENT;

    public static Animation popSwipeRemove() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 1.0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation popSwipeAdd() {
        TranslateAnimation animation = new TranslateAnimation(type, -0.3f, type, 0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation addSwipe() {
        TranslateAnimation animation = new TranslateAnimation(type, 1.0f, type, 0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation addSwipeRight() {
        TranslateAnimation animation = new TranslateAnimation(type, -1.0f, type, 0.0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation removeSwipeLeft() {
        TranslateAnimation animation = new TranslateAnimation(type, 0.0f, type, -1.0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation removeSwipe() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, -0.3f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation popPresentRemove() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 0f, type, 1f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation popPresentRemoveTop() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 0f, type, -1f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation popPresentRemoveTop(long time) {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 0f, type, -1f);
        animation.setDuration(time);
        return animation;
    }

    public static Animation popPresentAdd() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation addPresent() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 1f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation addPresentTop() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, -1f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation removePresent() {
        TranslateAnimation animation = new TranslateAnimation(type, 0f, type, 0f, type, 0f, type, 0f);
        animation.setDuration(animTime);
        return animation;
    }

    public static Animation bubble() {
        return new ScaleAnimation(0.3f, 1f, 0.3f, 1f, 0.5f, 0.5f);
    }

    public static void scaleIn(@NonNull View view, Action1<View> result) {
        view.setAlpha(1f);
        view.setScaleX(1f);
        view.setScaleY(1f);
        view.animate().alpha(0f).scaleX(0f).scaleY(0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (result != null) result.invoke(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (result != null) result.invoke(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(UIAnimation.animTime);
    }

    public static void scaleOut(@NonNull View view, Action1<View> result) {
        view.setAlpha(0f);
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.animate().alpha(1f).scaleX(1f).scaleY(1f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (result != null) result.invoke(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (result != null) result.invoke(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(UIAnimation.animTime);
    }

    public static void scaleOutIn(@NonNull View view, Action1<View> result) {
        view.setAlpha(0f);
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.animate().alpha(1f).scaleX(1f).scaleY(1f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                UIThread.run(() -> {
                    scaleIn(view, result);
                }, UIAnimation.animTime);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                UIThread.run(() -> {
                    scaleIn(view, result);
                }, UIAnimation.animTime);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(UIAnimation.animTime);
    }
}
