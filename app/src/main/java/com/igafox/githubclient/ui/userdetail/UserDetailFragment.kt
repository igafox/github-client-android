package com.igafox.githubclient.ui.userdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.igafox.githubclient.R
import com.igafox.githubclient.databinding.FragmentUserDetailBinding
import com.igafox.githubclient.ui.search.AppLoadStateAdapter
import com.igafox.githubclient.ui.search.SearchUserPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = UserDetailFragment()
    }

    private val viewModel: UserDetailViewModel by viewModels()

    private val pagingAdapter = SearchUserPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            itemAnimator = null
            setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = pagingAdapter.withLoadStateFooter(AppLoadStateAdapter())
            val divider = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
            this.addItemDecoration(divider)
        }
//        //スワイプ更新
//        binding.swiperefresh.setOnRefreshListener {
//            pagingAdapter.refresh()
//        }

        lifecycleScope.launch {
            viewModel.pagingFlow.collectLatest { value: PagingData<SearchUser> ->
//                binding.swiperefresh.isRefreshing = false
                pagingAdapter.submitData(value)
            }
        }

        lifecycleScope.launchWhenCreated {
            //新しいキーワードが検索された時にリストトップに移動させる処理
            pagingAdapter
                .loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.recyclerView.scrollToPosition(0)
                }
        }

        lifecycleScope.launchWhenCreated {
            //リフラッシュ時のみリフレッシュUI表示する処理
            pagingAdapter
                .loadStateFlow
                .collect {
//                    binding.swiperefresh.isRefreshing = it.refresh is LoadState.Loading
                }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.setQuery("iga")
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu_search_user, menu)
//        val searchItem = menu.findItem(R.id.search)
//        searchView = searchItem.actionView as SearchView
//        searchView.queryHint = "GitHubユーザー名で検索"
//        searchView.setOnQueryTextListener(this)
//    }

//    override fun onQueryTextChange(newText: String?): Boolean {
//        return false
//    }
//
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        if(query == null) return true
//        viewModel.setQuery(query)
//        searchView.onActionViewExpanded()
//        searchView.clearFocus()
//        return false
//    }

}