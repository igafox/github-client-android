package com.igafox.githubclient.ui.userdetail

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.igafox.githubclient.BR
import com.igafox.githubclient.R
import com.igafox.githubclient.data.model.Repo
import com.igafox.githubclient.databinding.FragmentUserDetailBinding
import com.igafox.githubclient.ui.extension.DpToPx
import com.igafox.githubclient.ui.search.AppLoadStateAdapter
import com.igafox.githubclient.ui.view.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.abs


@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private var errorSnackbar: Snackbar? = null

    companion object {

        private const val PARAM_USER_ID = "user_id"

        fun newInstance(userId: String): UserDetailFragment {
            return UserDetailFragment().apply {
                var bundle = Bundle()
                bundle.putString(PARAM_USER_ID, userId)
                arguments = bundle
            }
        }

    }

    private val viewModel: UserDetailViewModel by viewModels()

    private val pagingAdapter = UserDetailPagingAdapter { repo ->
        showRepoDetail(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //引数受け取り
        val userId = arguments?.getString("user_id")
        if (userId != null) {
            viewModel.setUserName(userId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            itemAnimator = null
            setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = pagingAdapter.withLoadStateFooter(AppLoadStateAdapter())
            addItemDecoration(SpaceItemDecoration(8.DpToPx(context)))
        }

        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val per = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
                binding.headerContent.alpha = (1 - per)
            })

        //スワイプ更新
        binding.swipeLayout.setOnRefreshListener {
            refresh()
        }

        lifecycleScope.launch {
            viewModel.pagingFlow.collectLatest { value: PagingData<Repo> ->
                pagingAdapter.submitData(value)
            }
        }

        lifecycleScope.launchWhenCreated {
            pagingAdapter
                .loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.recyclerView.scrollToPosition(0)
                }
        }

        //エラーハンドリング
        lifecycleScope.launchWhenCreated {
            viewModel.status.collectLatest { status ->
                showStatus(status)
            }
        }

        //ページングリフレッシュ時のみSwipeLayoutを表示する処理
        lifecycleScope.launchWhenCreated {
            pagingAdapter
                .loadStateFlow
                .collect {
                    binding.swipeLayout.isRefreshing = it.refresh is LoadState.Loading
                }
        }

    }

    private fun refresh() {
        viewModel.loadUser()
        pagingAdapter.refresh()
    }

    private fun showStatus(status: UserDetailViewStatus?) {
        status ?: return

        //読み込み時はSwipeLayout表示
        //binding.swipeLayout.isRefreshing = (status == UserDetailViewStatus.LOADING)

        //エラーハンドリング
        if (status == UserDetailViewStatus.FAILD) {
            errorSnackbar = view?.let {
                Snackbar.make(it, R.string.fetch_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { refresh() }
            }
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
            errorSnackbar = null
        }

    }

    private fun showRepoDetail(repo:Repo) {
        if(repo.htmlUrl.isBlank()) return

        val customTabsIntent = CustomTabsIntent.Builder()
            .build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(repo.htmlUrl))
    }

}