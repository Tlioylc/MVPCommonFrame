package com.tlioylc.client

import android.app.Application
import android.os.Build



/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/7/1510:53 AM
 *    desc   : 通用application
 */
class LihApplication : Application(){
    companion object {
        var instance : LihApplication? = null
        fun obtain() : LihApplication?{
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        if(Build.VERSION.SDK_INT >= 28)
            closeAndroidPDialog()
    }


    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}