package com.wang.taipeizooguide.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.data.model.Zoo
import kotlinx.android.synthetic.main.layout_adapter_zoo_list.view.*

typealias ZooClickListener = (Zoo) -> Unit

class ZooListAdapter : PagingDataAdapter<Zoo, ZooListAdapter.ViewHolder>(ZooDifferentiator) {

    private var _zooClickListener: ZooClickListener? = null
    var zooClickListener
        get() = _zooClickListener
        set(listener) {
            _zooClickListener = listener
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ZooListAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { zooAtPosition ->
            holder.itemView.zoo_name.text = "${zooAtPosition.E_Name} | ${zooAtPosition.E_Category}"
            holder.itemView.zoo_memo.text =
                if (zooAtPosition.E_Memo.isNotEmpty()) zooAtPosition.E_Memo else "N/A"
            Glide.with(holder.itemView.zoo_image)
                .load(zooAtPosition.E_Pic_URL)
                .apply(RequestOptions().centerCrop())
                .into(holder.itemView.zoo_image)

            holder.itemView.setOnClickListener { _zooClickListener?.invoke(zooAtPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_adapter_zoo_list, parent, false)
        )
    }

    object ZooDifferentiator : DiffUtil.ItemCallback<Zoo>() {
        override fun areItemsTheSame(oldItem: Zoo, newItem: Zoo): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Zoo, newItem: Zoo): Boolean {
            return oldItem == newItem
        }

    }
}