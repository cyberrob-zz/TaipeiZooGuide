package com.wang.taipeizooguide.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.data.model.Arboretum
import kotlinx.android.synthetic.main.layout_recycler_view_adapter.view.*

typealias ArboretumClickListener = (Arboretum) -> Unit

class ArboretumListAdapter :
    PagingDataAdapter<Arboretum, ArboretumListAdapter.ViewHolder>(ArboretumDifferentiator) {

    private var _arboretumClickListener: ArboretumClickListener? = null
    var arboretumClickListener
        get() = _arboretumClickListener
        set(listener) {
            _arboretumClickListener = listener
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { arboretumAtPosition ->
            holder.itemView.item_name.text = "${arboretumAtPosition.F_Name_En}"
            holder.itemView.item_memo.text =
                if (arboretumAtPosition.F_Update.isNotEmpty()) arboretumAtPosition.F_Update else "N/A"
            Glide.with(holder.itemView.preview_image)
                .load(arboretumAtPosition.F_Pic01_URL)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.ic_pixeltrue_error)
                .into(holder.itemView.preview_image)

            holder.itemView.setOnClickListener { _arboretumClickListener?.invoke(arboretumAtPosition) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recycler_view_adapter, parent, false)
        )
    }

    object ArboretumDifferentiator : DiffUtil.ItemCallback<Arboretum>() {
        override fun areItemsTheSame(oldItem: Arboretum, newItem: Arboretum): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Arboretum, newItem: Arboretum): Boolean {
            return oldItem == newItem
        }

    }
}