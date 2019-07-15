package com.tlioylc.client.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 *    author : tlioylc
 *    e-mail : tlioylc@gmail.com
 *    date   : 2019/6/5上午10:18
 *    desc   : 通用RecyclerView 适配器
 */
abstract class NormalListCommonAdapter<T ,V : RecyclerView.ViewHolder?>(val layoutRsd : Int) : RecyclerView.Adapter<V>() {
    val list : MutableList<T> = ArrayList()
    fun initList(data : List<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun updateList(data : List<T>){
        var currentPosition : Int = list.size
        list.addAll(data)
        notifyItemRangeChanged(currentPosition, list.size)
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    abstract fun getViewHolder(view: View): V
    abstract fun updateViewHolder(h: V, o: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val view = LayoutInflater.from(parent.context).inflate(layoutRsd, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        updateViewHolder(holder, list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}