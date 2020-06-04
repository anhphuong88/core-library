package com.app.core.ui.event;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class RecycleScrollStateObservable extends Observable<Integer> {
    private final WeakReference<RecyclerView> refView;

    RecycleScrollStateObservable(@NonNull RecyclerView view) {
        this.refView = new WeakReference<>(view);
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        PreCondition.mainThread(observer, () -> {
            if (refView.get() != null) {
                Listener listener = new Listener(refView.get(), observer);
                observer.onSubscribe(listener);
                refView.get().addOnScrollListener(listener.onScrollListener);
            }
            return null;
        });
    }

    static final class Listener extends MainThreadDisposable {
        private WeakReference<RecyclerView> refView;
        private RecyclerView.OnScrollListener onScrollListener;

        Listener(@NonNull RecyclerView view, Observer<? super Integer> observer) {
            refView = new WeakReference<>(view);
            onScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (!isDisposed()) {
                        observer.onNext(newState);
                    }
                }
            };
        }
        @Override
        protected void onDispose() {
            if (refView != null && refView.get() != null) {
                refView.get().removeOnScrollListener(onScrollListener);
            }
        }
    }
}
