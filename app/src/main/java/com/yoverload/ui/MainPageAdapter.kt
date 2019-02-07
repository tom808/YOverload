package com.yoverload.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yoverload.R
import com.yoverload.data.db.Item

/**
 * Created by tom.egan on 07-Jan-2019.
 */
class MainPageAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<MainPageAdapter.MainPageAdapterViewHolder>() {

    private var listItems : List<Item> = listOf()

    class MainPageAdapterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.findViewById<TextView>(R.id.tv_list_item)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType : Int): MainPageAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.list_item, viewGroup, false)
        return MainPageAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(viewHolder: MainPageAdapterViewHolder, position: Int) {
        viewHolder.itemText.text = listItems.get(position).title
    }

    fun setPageData (items: List<Item>) {
        listItems = items
        notifyDataSetChanged()
    }
}