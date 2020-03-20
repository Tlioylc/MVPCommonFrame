package com.tlioylc.client.module.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tlioylc.client.R;
import com.tlioylc.client.module.base.BaseActivity;
import com.tlioylc.client.module.widget.EmptyView;
import com.tlioylc.client.module.widget.ProgressWebView;
import com.tlioylc.client.module.widget.TransparentTitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/3/7下午4:35
 * desc   : 通用webActivity
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.web_activity_title)
    TransparentTitleBarView title;
    @BindView(R.id.web_activity_webview)
    ProgressWebView webview;
    @BindView(R.id.web_activity_empty_view)
    EmptyView emptyView;

    private String url;
    private boolean loadError = false;

    private InterfaceJSUtil interfaceJSUtil;
    public static void open(Context context,String url){
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        title.setTitleBarLeftImg(R.mipmap.ic_common_back, v -> finish());
        title.setTitle("正在加载...");
        setUpWebSetting(webview.getSettings());

        webview.requestFocusFromTouch();
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
            }

            // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                // 在这里显示自定义错误页
                loadError = true;
                hideLoadDialog();
                emptyView.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
            }

            // 新版本，只会在Android6及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    // 在这里显示自定义错误页
                    loadError = true;
                    hideLoadDialog();
                    emptyView.setVisibility(View.VISIBLE);
                    webview.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String s = view.getTitle();
                if(!TextUtils.isEmpty(s))
                title.setTitle(s);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadError = false;
                webview.loadUrl(url);
                setEmptyView(url);
                return false;
            }

        });
        /**
         * 如果需要设置agent
         * StringBuilder stringBuilder = new StringBuilder(webview.getSettings().getUserAgentString());
         * webview.getSettings().setUserAgentString(stringBuilder.append("/xxx/").toString());
         */


        interfaceJSUtil = new InterfaceJSUtil(WebActivity.this);
        webview.addJavascriptInterface(interfaceJSUtil, "JavaBridgeInterface");
        webview.loadUrl(url);
        setEmptyView(url);
    }


    private void setEmptyView(final String url) {
        emptyView.updateView(R.string.web_activity_net_error_text, v -> {
            loadError = false;
            webview.clearView();
            webview.clearCache(true);
            webview.clearHistory();
            webview.loadUrl(url);
            title.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void onPause() {
        webview.onPause();
        webview.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webview.onResume();
        webview.resumeTimers();
        super.onResume();
    }

    /**
     * webView配置
     *
     * @param webSettings
     */
    public void setUpWebSetting(WebSettings webSettings) {
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
//调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
// 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
//设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);

        //多窗口
        webSettings.supportMultipleWindows();
//允许访问文件
        webSettings.setAllowFileAccess(true);
//开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//打开webview中缓存,部分机型设置cache-control无效，判断如果是app.diifun.com域名下的url，直接设置不缓存
//<<<<<<< HEAD
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
// 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
//开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        //设置webView缓存路径
        String cacheDirPath = this.getCacheDir().getAbsolutePath() + "/webcache";
        webSettings.setDatabasePath(cacheDirPath);
        webSettings.setAppCachePath(cacheDirPath);
        webSettings.setAppCacheEnabled(true);

        webSettings.setAllowUniversalAccessFromFileURLs(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void onBackPressed() {
        // super.onBackPressed();//注释掉这行,back键不退出activity
        if (loadError)
            super.onBackPressed();
        else {
            if (webview.canGoBack()) {
                if (webview.getUrl().equals(url)) {
                    super.onBackPressed();
                } else {
                    webview.goBack();
                }
            } else {
                super.onBackPressed();
            }
        }
    }


}
