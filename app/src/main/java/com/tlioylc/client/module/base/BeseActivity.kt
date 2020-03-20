package com.tlioylc.client.module.base

import android.app.Activity
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.tlioylc.client.module.widget.LoadDialogView
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1511:05 AM
 *    desc   :
 */
open class BaseActivity : AppCompatActivity(), IBaseView {

    private var loadDialog: Dialog? = null
    /**
     * 判断手机是否是魅族
     *
     * @return
     */
    private val isFlyme: Boolean
        get() {
            try {
                val method = Build::class.java.getMethod("hasSmartBar")
                return method != null
            } catch (e: Exception) {
                return false
            }

        }

    /**
     * 设置OPPO手机状态栏字体为黑色(colorOS3.0,6.0以下部分手机)
     *
     * @param lightStatusBar
     * @param activity
     */
    private val SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010

    override fun showLoading() {
        showLoadDialog(true, true, null)
    }

    override fun hideLoading() {
        hideLoadDialog()
    }

    override fun showNetError() {

    }

    override fun finishRefresh() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//设置为无标题
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//设置为竖屏
        } catch (e: Exception) {
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val window = window
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        BarUtils.setStatusBarLightMode(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//6.0 以上配置全透明，系统栏字体颜色可改动
            val decor = window.decorView
            var ui = decor.systemUiVisibility
            ui = ui or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decor.systemUiVisibility = ui

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.parseColor("#FFFFFF")
            } else {
                window.statusBarColor = Color.parseColor("#BDBDBD")
            }
        }
        setStatusTextColor(true, this)
    }

    /**
     * 显示和隐藏状态栏
     *
     * @param show
     */
    protected fun setStatusBarVisible(show: Boolean) {
        if (show) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    /**
     * 设置状态栏文字色值为深色调
     *
     * @param useDart  是否使用深色调
     * @param activity
     */
    fun setStatusTextColor(useDart: Boolean, activity: Activity) {
        if (isFlyme) {
            //魅族
            processFlyMe(useDart, activity)
        } else if (isMIUI) {
            //小米
            setMiuiStatusBarDarkMode(activity, useDart)
        } else if (Build.MANUFACTURER.equals("OPPO", ignoreCase = true)) {
            //OPPO
            setOPPOStatusTextColor(useDart, activity)
        }
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private fun processFlyMe(isLightStatusBar: Boolean, activity: Activity) {
        val lp = activity.window.attributes
        try {
            val instance = Class.forName("android.view.WindowManager\$LayoutParams")
            val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
            val field = instance.getDeclaredField("meizuFlags")
            field.isAccessible = true
            val origin = field.getInt(lp)
            if (isLightStatusBar) {
                field.set(lp, origin or value)
            } else {
                field.set(lp, value.inv() and origin)
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    private fun setOPPOStatusTextColor(lightStatusBar: Boolean, activity: Activity) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var vis = window.decorView.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightStatusBar) {
                vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (lightStatusBar) {
                vis = vis or SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT
            } else {
                vis = vis and SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT.inv()
            }
        }
        window.decorView.systemUiVisibility = vis
    }


    /**
     * 设置UI系统栏沉浸
     * 界面UI将移至系统状态栏高度
     * 需考虑安全距离
     */
    fun initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    fun showLoadDialog(canCancel: Boolean, isGray: Boolean, text: String?) {
        if (isFinishing) {
            return
        }

        if (loadDialog == null || !loadDialog?.isShowing!!) {
            loadDialog = LoadDialogView.createDialog(this, canCancel, isGray, text)
        }

        if (!loadDialog?.isShowing!!) {
            loadDialog?.show()
        }
    }

    fun hideLoadDialog() {
        if (loadDialog?.isShowing!!) {
            loadDialog?.cancel()
        }
        loadDialog = null
    }

    companion object {


        private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
        private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
        private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

        /**
         * 判断手机是否是小米
         *
         * @return
         */
        private val isMIUI: Boolean
            get() {
                try {
                    val prop = Properties()
                    prop.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))

                    return (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                            || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                            || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null)
                } catch (e: IOException) {
                    return false
                }

            }

        /**
         * 设置OPPO手机状态栏字体为黑色(colorOS3.0,6.0以下部分手机)
         * //小米系统字体颜色
         */
        fun setMiuiStatusBarDarkMode(activity: Activity, darkmode: Boolean): Boolean {
            val clazz = activity.window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false

        }
    }


}
