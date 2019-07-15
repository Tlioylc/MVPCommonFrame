package com.tlioylc.client.module.bese

import com.blankj.utilcode.util.BarUtils
import com.tlioylc.client.BuildConfig

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:31 AM
 *    desc   :
 */
object BaseConfig {
    val DB_VERSION = 2 //数据库的版本
    val apiHost = if (BuildConfig.IS_RELEASE) "1" else "2"
    val urlHost = if (BuildConfig.IS_RELEASE) "1" else "2"
    val CDNHost = "3"

    val STATUS_BAR_SAFE_HEIGHT = BarUtils.getStatusBarHeight() + 6//界面最顶部View距屏幕上部安全距离,状态栏高度+额外6像素容错

}
