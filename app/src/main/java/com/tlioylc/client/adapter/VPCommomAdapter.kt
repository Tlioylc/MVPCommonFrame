package com.tlioylc.client.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.tlioylc.client.module.base.BaseFragment
import java.util.*

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/6/5上午11:13
 *    desc   : 通用viewPager fragment适配器
 */
class VPCommomAdapter(fm: FragmentManager, var list: MutableList<BaseFragment> = ArrayList()) : FragmentPagerAdapter(fm) {
    private var titles: Array<String>? = null
    fun setData(list: List<BaseFragment>, titles : Array<String>) {
        this.list.addAll(list)
        this.titles = titles
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position)
    }

}