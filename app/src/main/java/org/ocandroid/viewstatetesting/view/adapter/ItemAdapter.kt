package org.ocandroid.viewstatetesting.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ocandroid.viewstatetesting.R
import org.ocandroid.viewstatetesting.databinding.ViewItemBinding
import org.ocandroid.viewstatetesting.model.repository.data.ItemModel

class ItemAdapter: ListAdapter<ItemModel, ItemAdapter.ItemViewHolder>(ItemModelDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: ViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: ItemModel) {
            binding.apply {
                name.text = itemModel.name
                price.text = itemModel.price
                newIcon.visibility = if (itemModel.isNew) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                foodTypeIcon.setImageResource(
                    if (itemModel.isFastFood) {
                        R.drawable.ic_outline_fastfood_24
                    } else {
                        R.drawable.ic_outline_local_dining_24
                    }
                )
            }
        }
    }

    object ItemModelDiffCallback: DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem == newItem
    }
}