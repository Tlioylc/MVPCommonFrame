package com.tlioylc.client.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/1/8下午12:08
 * desc   :
 */
public class RxView {
    /**
     * 监听onclick事件防抖动
     *
     * @param view
     * @return
     */
    @CheckResult
    @NonNull
    public static Observable clicks(@NonNull View... view) {
        if(view == null){
            return null;
        }
        return Observable.create(new RxViewClickOnSubscribe(view));
    }
}
