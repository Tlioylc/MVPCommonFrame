package com.tlioylc.client.module.bese

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:06 AM
 *    desc   : View基类
 */
interface IBaseView {

    /**
     * 显示加载动画
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示网络错误，modify 对网络异常在 BaseTitleBarActivity 和 BaseFragment 统一处理
     */
    fun showNetError()

    /**
     * 完成刷新, 新增控制刷新
     */
    fun finishRefresh()
}