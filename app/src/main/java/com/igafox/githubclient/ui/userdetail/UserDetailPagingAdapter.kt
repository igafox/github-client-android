package com.igafox.githubclient.ui.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igafox.githubclient.databinding.ItemUserBinding

private val diffCallBack = object: DiffUtil.ItemCallback<SearchUser>() {
    override fun areItemsTheSame(oldItem: SearchUser, newItem: SearchUser): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: SearchUser, newItem: SearchUser): Boolean = oldItem == newItem
}

class UserDetailPagingAdapter :PagingDataAdapter<SearchUser, UserDetailPagingAdapter.ViewHolder>(
    diffCallBack
) {

    inner class ViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = getItem(position)
    }

}