package net.l1ngdtkh3.swipe_and_delete

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.l1ngdtkh3.swipe_and_delete.databinding.RowItemBinding

class ItemAdapter(private val listener: ItemCallBack) : ListAdapter<ItemList, ItemAdapter.ItemViewHolder>(ItemDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder =
        ItemViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemList) {
            with(binding) {
                details.text = item.data
                front.visibility = if (item.swiped) View.GONE else View.VISIBLE
                itemContainer.cancel.setOnClickListener {
                    front.visibility = View.VISIBLE
                    item.swiped = false
                    listener.onCancel(item, this@ItemViewHolder)
                }
                itemContainer.delete.setOnClickListener {
                    listener.onDelete(item)
                }
            }
        }

        fun update() {
            getItem(absoluteAdapterPosition).swiped = true
            notifyItemChanged(absoluteAdapterPosition)
        }
    }
}

private class ItemDiff : DiffUtil.ItemCallback<ItemList>() {
    override fun areItemsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem.data == newItem.data
                && oldItem.swiped == newItem.swiped
    }
}