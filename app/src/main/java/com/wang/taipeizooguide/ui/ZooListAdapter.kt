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

class ZooListAdapter : PagingDataAdapter<Zoo, ZooListAdapter.ViewHolder>(ZooDifferentiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ZooListAdapter.ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.itemView.zoo_name.text = it.E_Name
            Glide.with(holder.itemView.zoo_image)
                .load(it.E_Pic_URL)
                //.transform(BlurTransformation(holder.itemView.context))
                .apply(RequestOptions().centerCrop())
                .into(holder.itemView.zoo_image)
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