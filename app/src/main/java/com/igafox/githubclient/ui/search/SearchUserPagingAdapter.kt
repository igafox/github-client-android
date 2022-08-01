package com.igafox.githubclient.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igafox.githubclient.data.model.User
import com.igafox.githubclient.databinding.ItemUserBinding

private val diffCallBack = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
}

class SearchUserPagingAdapter(private val onClick: (User) -> Unit) :
    PagingDataAdapter<User, SearchUserPagingAdapter.ViewHolder>(diffCallBack) {

    inner class ViewHolder(val binding: ItemUserBinding, val onClick: (User) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.user ?: return@setOnClickListener
                onClick(binding.user!!)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = getItem(position)
    }

}