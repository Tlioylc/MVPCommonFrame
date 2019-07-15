package com.tlioylc.client.module.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.tlioylc.client.command.CommandDispatcher;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/20上午11:47
 * desc   : webView注入接口
 */
public class InterfaceJSUtil {
    private Activity activity;

    public InterfaceJSUtil(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void commandHandle(final String gotoUrl) {
        LogUtils.e("============"+gotoUrl);
        activity.runOnUiThread(() -> CommandDispatcher.dispatch(activity, gotoUrl));
    }
}
