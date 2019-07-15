package com.tlioylc.client.command;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Comment placeholder.
 * Created by danliu on 2018/7/13.
 */

public abstract class Command {

    @JSONField(name = "action")
    private String mAction;

    abstract boolean execute(@NonNull final Context context);

    public void setAction(String action) {
        mAction = action;
    }

    public String getAction() {
        return mAction;
    }
}
