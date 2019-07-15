package com.tlioylc.client.command;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.blankj.utilcode.util.ToastUtils;
import com.tlioylc.client.module.web.WebActivity;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/7/1511:59 AM
 * desc   : 命令操作
 */
public class Commands {

    public static final class DefaultCommand extends Command {
        @Override
        boolean execute(@NonNull final Context context) {
            ToastUtils.showShort("客户端暂不支持该操作，请更新客户端后重试.");
            return false;
        }
    }

    public static void turnToH5Inside(Context context, String url) {
        WebActivity.open(context, url);
    }

    public static class H5OpenInside extends Command {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        boolean execute(@NonNull Context context) {
            turnToH5Inside(context, url);
            return true;
        }
    }

    public static class H5OpenOutside extends Command {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        boolean execute(@NonNull Context context) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return true;
        }
    }
}
