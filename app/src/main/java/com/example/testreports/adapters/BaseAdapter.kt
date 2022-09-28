package com.example.testreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



abstract class BaseAdapter constructor(
    var onItemClicker: OnItemClicker?,
    protected var itemList: List<kotlin.Any>,
    private val layoutResId: Int , private val adapterType: Int = -1)
    : RecyclerView.Adapter<BaseAdapter.Holder>() {




    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            onItemClicker?.let {
                it.onItemClick(position,itemList[position],adapterType)
            }

        }
        holder.itemView.bind(item, position)
    }


    protected open fun View.bind(item: Any, position: Int) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


    interface OnItemClicker{
       fun onItemClick(position:Int,data:Any,adapterType:Int)
    }
}