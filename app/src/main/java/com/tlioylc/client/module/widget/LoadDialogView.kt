package com.tlioylc.client.module.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.tlioylc.client.R

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:11 AM
 *    desc   : 加载时UI展示
 */
object LoadDialogView {

    fun createDialog(context: Context, cancel: Boolean, isGray: Boolean, alertText: String?): Dialog {
        val grayLayout: View?
        val whiteLayout: View?


        val inflater = LayoutInflater.from(context)
        val v : View? = inflater.inflate(R.layout.view_load_layout_dialog, null)// 得到加载view
        val layout : View? = v?.findViewById(R.id.dialog_view)// 加载布局


        grayLayout = v?.findViewById(R.id.load_layout_gray_relativelayout)
        val grayCircle : ImageView? = v?.findViewById(R.id.load_layout_gray_circle_imageview)

        whiteLayout = v?.findViewById(R.id.load_layout_white_relativelayout)
        val whiteCircle : ImageView? = v?.findViewById(R.id.load_layout_white_circle_imageview)

        val operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        if (isGray) {
            grayLayout?.visibility = View.VISIBLE
            whiteLayout?.visibility = View.GONE
            grayCircle?.animation = operatingAnim
        } else {
            grayLayout?.visibility = View.GONE
            whiteLayout?.visibility = View.VISIBLE
            whiteCircle?.animation = operatingAnim
        }
        val alertTextView  : TextView?= v?.findViewById(R.id.load_layout_text)
        if (!TextUtils.isEmpty(alertText)) {
            alertTextView?.text = alertText
            alertTextView?.visibility = View.VISIBLE
        }

        val dialog = Dialog(context, R.style.LoadDialogSys)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(cancel)
        dialog.setContentView(layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
        dialog.setOnCancelListener {
            grayCircle?.clearAnimation()
            whiteCircle?.clearAnimation()
        }
        return dialog
    }

}
