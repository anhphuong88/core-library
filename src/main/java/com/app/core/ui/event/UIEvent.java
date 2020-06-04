package com.app.core.ui.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.reactivex.Observable;

public final class UIEvent {
    public static <T extends View> Observable<T> click(@NonNull T view) {
        return new ClickObservable<>(view);
    }

    public static Observable<Integer> scrollState(@NonNull RecyclerView view) {
        return new RecycleScrollStateObservable(view);
    }

    public static <T extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> Observable<T> dataChanged(@NonNull T adapter) {
        return new AdapterDataObservable<>(adapter);
    }
}
