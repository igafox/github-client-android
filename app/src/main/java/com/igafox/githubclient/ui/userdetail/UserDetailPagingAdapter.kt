package com.igafox.githubclient.ui.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igafox.githubclient.data.model.Repo
import com.igafox.githubclient.databinding.ItemRepoBinding

private val diffCallBack = object: DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem
}

class UserDetailPagingAdapter :PagingDataAdapter<Repo, UserDetailPagingAdapter.ViewHolder>(
    diffCallBack
) {

    inner class ViewHolder(val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.repo = getItem(position)
    }

}