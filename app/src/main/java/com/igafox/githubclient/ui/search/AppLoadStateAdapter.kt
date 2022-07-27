package com.igafox.githubclient.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.igafox.githubclient.databinding.ItemUserBinding
import com.igafox.githubclient.databinding.ViewProgressbarBinding

class AppLoadStateAdapter : LoadStateAdapter<AppLoadStateAdapter.LoadViewHolder>() {

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.binding.progressbar.visibility =
            if (loadState is LoadState.Loading) View.VISIBLE else View.GONE

        holder.binding.retry.visibility =
            if (loadState is LoadState.Error) View.VISIBLE else View.GONE

        holder.binding.retry.setOnClickListener {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        return LoadViewHolder(
            ViewProgressbarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadViewHolder(val binding: ViewProgressbarBinding) :
        RecyclerView.ViewHolder(binding.root)
}