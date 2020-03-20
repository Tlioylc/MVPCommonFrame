package com.tlioylc.client.module.base

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1512:00 PM
 *    desc   :
 */
interface ILoadDataView<T> : IBaseView {

    val isFinish: Boolean
    /**
     * 加载数据
     * @param data 数据
     */
    fun loadData(data: T)

    /**
     * 加载更多
     * @param data 数据
     */
    fun loadMoreData(data: T)

    /**
     * 没有数据
     */
    fun loadNoData(isLoadMore: Boolean)

    /**
     * 获取数据失败
     */
    fun loadError()
}