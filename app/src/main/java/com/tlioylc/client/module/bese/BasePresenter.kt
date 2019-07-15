package com.tlioylc.client.module.bese

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:59 AM
 *    desc   :
 */
abstract class IBasePresenter {
    /**
     * 获取网络数据，更新界面
     */
    /**
     * 获取网络数据，更新界面
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    abstract fun getData(isRefresh: Boolean)

    /**
     * 加载更多数据.不一定实现
     */
    fun getMoreData(offset: Int) {}

    companion object {
        protected val MAX_DATA_LIMIT = 20
    }
}
