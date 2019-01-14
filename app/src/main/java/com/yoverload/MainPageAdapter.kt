package com.yoverload

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yoverload.network.Item

/**
 * Created by tom.egan on 07-Jan-2019.
 */
class MainPageAdapter : RecyclerView.Adapter<MainPageAdapter.MainPageAdapterViewHolder>() {

    private var listItems : MutableList<Item>? = null

    class MainPageAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.findViewById<TextView>(R.id.tv_list_item)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType : Int): MainPageAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.list_item, viewGroup, false)
        return MainPageAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: MainPageAdapterViewHolder, position: Int) {
        viewHolder.itemText.text = listItems?.get(position)?.title ?: ""
    }

    fun setPageData (items: MutableList<Item>) {
        listItems = items
        notifyDataSetChanged()
    }

    fun setPageData (item: Item) {
        listItems?:let {
            listItems = ArrayList<Item>()
        }
        listItems?.add(item)
        notifyDataSetChanged()
    }
}