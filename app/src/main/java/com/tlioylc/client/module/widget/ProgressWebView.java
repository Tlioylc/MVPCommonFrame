package com.tlioylc.client.module.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/9上午11:40
 * desc   : 带进度条webView
 */
public class ProgressWebView extends WebView {
    private ProgressBar mProgressBar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(2));
        mProgressBar.setLayoutParams(layoutParams);

        setColors(mProgressBar,Color.parseColor("#ffffff"),Color.parseColor("#FF0D0D"));
        mProgressBar.setVisibility(VISIBLE);
        mProgressBar.setMax(100);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setColors(ProgressBar progressBar, int backgroundColor, int progressColor) {
        //Background
        ClipDrawable bgClipDrawable = new ClipDrawable(new ColorDrawable(backgroundColor), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        bgClipDrawable.setLevel(10000);
        //Progress
        ClipDrawable progressClip = new ClipDrawable(new ColorDrawable(progressColor), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //Setup LayerDrawable and assign to progressBar
        Drawable[] progressDrawables = {bgClipDrawable, progressClip/*second*/, progressClip};
        LayerDrawable progressLayerDrawable = new LayerDrawable(progressDrawables);
        progressLayerDrawable.setId(0, android.R.id.background);
        progressLayerDrawable.setId(1, android.R.id.secondaryProgress);
        progressLayerDrawable.setId(2, android.R.id.progress);

        progressBar.setProgressDrawable(progressLayerDrawable);
    }
}