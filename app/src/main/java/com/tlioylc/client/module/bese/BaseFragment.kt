package com.tlioylc.client.module.bese

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tlioylc.client.module.widget.LoadDialogView

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:56 AM
 *    desc   :
 */
abstract class BaseFragment : Fragment(), IBaseView {
    private var contentView: View? = null
    private var loadDialog: Dialog? = null
    /**
     * 设置fragment view id
     * @return
     */
    abstract val layoutId: Int

    /**
     * 判断界面是否被回收
     */
    val isInValid: Boolean
        get() = activity == null || activity!!.isFinishing || !isAdded

    /**
     * 获取数据，刷新ui
     * @return
     */
    abstract fun showView()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (contentView == null) {
            contentView = inflater.inflate(layoutId, null)
        }
        if (contentView != null) {
            return contentView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideLoadDialog()
        if (contentView != null) {
            (contentView!!.parent as ViewGroup).removeView(contentView)
        }
    }

    /**
     * 生命周期
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            hideLoadDialog()
        }
    }

    /**
     * 加载动画
     * @param canCancel 是否可以取消
     * @param isGray 是否灰色
     * @param text 文字提醒
     */
    @JvmOverloads
    fun showLoadDialog(canCancel: Boolean, isGray: Boolean, text: String? = null) {
        if (activity == null || activity!!.isFinishing || isHidden || !isAdded) {
            return
        }

        if (loadDialog == null || !loadDialog!!.isShowing) {
            loadDialog = LoadDialogView.createDialog(activity!!, canCancel, isGray, text)
        }

        if (!loadDialog!!.isShowing) {
            loadDialog!!.show()
        }
    }

    fun hideLoadDialog() {
        if (loadDialog != null && loadDialog!!.isShowing) {
            loadDialog!!.cancel()
        }
        loadDialog = null
    }

    override fun showLoading() {

    }

    override fun hideLoading() {}

    override fun showNetError() {

    }

    override fun finishRefresh() {

    }
}