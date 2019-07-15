package com.tlioylc.client.command;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Set;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/20上午11:57
 * desc   : 命令跳转
 */

public class CommandDispatcher {
    //url示例 test://h5_open_inside?url = "dwadwadada"

    private static final HashMap<String,Class<? extends Command>> sCommandRepo = new HashMap<>();

    static {
        sCommandRepo.put("h5_open_inside", Commands.H5OpenInside.class);//内部打开URL
        sCommandRepo.put("h5_open_outside", Commands.H5OpenOutside.class);//外部打开URL

    }

    public static boolean dispatch(final Context context, final String url) {
        final Command command = parseUrl(url);
        return command.execute(context);
    }


    private static Command parseUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            return new Commands.DefaultCommand();
        }
        Uri parsed = Uri.parse(url);
        final String scheme = parsed.getScheme();

        if (!TextUtils.equals(scheme, "test")) {
            return new Commands.DefaultCommand();
        }
        final String host = parsed.getHost();
        final Set<String> paramNames = parsed.getQueryParameterNames();
        final JSONObject queryParams = new JSONObject();
        for (String param : paramNames) {
            if(param.equals("url") && (host.equals("h5_open_inside") || host.equals("h5_open_outside"))){
                String openUrl = url.substring(url.indexOf("=")+1);
                if(!TextUtils.isEmpty(openUrl)){
                    queryParams.put(param, openUrl.trim());
                }
            }else {
                queryParams.put(param, URLDecoder.decode(parsed.getQueryParameter(param)));
            }
        }
        return parseJson(host, queryParams);
    }

    private static Command parseJson(@NonNull final String action, JSONObject data) {
        if (data == null) {
            data = new JSONObject();
        }
        final Class<? extends Command> cmdCls = sCommandRepo.get(action);
        if (cmdCls == null) {
            return new Commands.DefaultCommand();
        }
        return JSON.parseObject(data.toString(), cmdCls);
    }

}

