package com.app.core.ui.event;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class AdapterDataObservable<T extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> extends Observable<T> {
    private final WeakReference<T> ref;

    AdapterDataObservable(@NonNull T adapter) {
        this.ref = new WeakReference<>(adapter);
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        PreCondition.mainThread(observer, () -> {
            if (ref.get() != null) {
                Listener<T> listener = new Listener<>(ref.get(), observer);
                observer.onSubscribe(listener);
                ref.get().registerAdapterDataObserver(listener.adapterDataObserver);
            }
            return null;
        });
    }

    static final class Listener<T extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> extends MainThreadDisposable {
        private WeakReference<T> ref;
        private RecyclerView.AdapterDataObserver adapterDataObserver;

        Listener(@NonNull T adapter, Observer<? super T> observer) {
            ref = new WeakReference<>(adapter);
            this.adapterDataObserver = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    if (!isDisposed()) {
                        observer.onNext(ref.get());
                    }
                }
            };

        }
        @Override
        protected void onDispose() {
            if (ref != null && ref.get() != null) {
                ref.get().unregisterAdapterDataObserver(adapterDataObserver);
            }
        }
    }
}
