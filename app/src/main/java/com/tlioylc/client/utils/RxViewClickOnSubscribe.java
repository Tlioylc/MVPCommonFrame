package com.tlioylc.client.utils;

import android.os.Looper;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/1/8下午12:10
 * desc   :
 */
public class RxViewClickOnSubscribe implements Observable.OnSubscribe<Subscriber> {
    final View[] viewArray;

    RxViewClickOnSubscribe(View... view) {
        this.viewArray = view;
    }
    @Override
    public void call(final Subscriber subscriber) {
        if (!checkUiThread()) {
            return;
        }
        if(viewArray != null && viewArray.length != 0){
            for(View view1 : viewArray){
                if(view1 != null)
                    setClick(subscriber,view1);
            }
        }
    }


    public void setClick(final Subscriber subscriber,final View viewItem){
        View.OnClickListener listener = v -> {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(viewItem);
            }
        };
        viewItem.setOnClickListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                viewItem.setOnClickListener(null);
            }
        });
    }

    public boolean checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
//            throw new IllegalStateException(
//                    "Must be called from the main thread. Was: " + Thread.currentThread());
            return false;
        }
        return true;
    }
}
