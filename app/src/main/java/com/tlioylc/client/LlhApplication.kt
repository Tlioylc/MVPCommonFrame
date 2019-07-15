package com.tlioylc.client

import android.app.Application
import android.os.Parcel
import android.os.Parcelable

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
    }


}