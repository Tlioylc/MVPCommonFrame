package com.tlioylc.client.module.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.tlioylc.client.R
import com.tlioylc.openannotation.annotation.OpenBuilder
import com.tlioylc.openannotation.annotation.Require

@OpenBuilder
class MainActivity : AppCompatActivity() {

    @Require
    var toastText : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastUtils.showShort(toastText)
    }
}
