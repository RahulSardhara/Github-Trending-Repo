package com.graphybyte.githubtrendingrepo.ui.list

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.graphybyte.githubtrendingrepo.R
import com.graphybyte.githubtrendingrepo.core.BaseActivity
import com.graphybyte.githubtrendingrepo.core.Event
import com.graphybyte.githubtrendingrepo.core.OnItemClickListener
import com.graphybyte.githubtrendingrepo.core.RecyclerListAdapter
import com.graphybyte.githubtrendingrepo.databinding.ActivityMainBinding
import com.graphybyte.githubtrendingrepo.databinding.ItemGithubRepoBinding
import com.graphybyte.githubtrendingrepo.databinding.ItemLangBinding
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity
import com.graphybyte.githubtrendingrepo.ui.details.GithubRepoDetailActivity
import com.graphybyte.githubtrendingrepo.utils.*
import com.graphybyte.githubtrendingrepo.utils.AppConstants.GITHUB_REPO
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class GithubRepoListActivity : BaseActivity<ActivityMainBinding>() {

    private val githubRepoViewModel: GithubRepoViewModel by viewModels()



    private val githubRepoRecyclerViewAdapter =
        RecyclerListAdapter<GithubEntity, ItemGithubRepoBinding>(
            diffUtil = eventsDiffUtil,
            layoutId = R.layout.item_github_repo,
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(data: Any, position: Int, view: View) {
                    onItemClicked(data as GithubEntity)
                }
            }
        )
    private val langRecyclerViewAdapter =
        RecyclerListAdapter<String, ItemLangBinding>(
            diffUtil = languageDiffUtil,
            layoutId = R.layout.item_lang,
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(data: Any, position: Int, view: View) {
                }
            }
        )

    private fun onItemClicked(githubEntity: GithubEntity?) {
        githubEntity?.let {
            openActivity(GithubRepoDetailActivity::class.java) {
                putSerializable(GITHUB_REPO, it)
            }
        }
    }

    override fun setActivityView() = R.layout.activity_main

    override fun init() {
        hideStatusBar()
        githubRepoViewModel.apply {
            binding.viewModel = this
            binding.progressBar.visibility = View.VISIBLE
        }
        binding.githubRV.adapter = githubRepoRecyclerViewAdapter
        binding.languageRV.adapter = langRecyclerViewAdapter
        githubRepoViewModel.getGithubRepoList()
        binding.githubRV.addOnScrollListener(object : RecyclerViewPagination(binding.githubRV) {
            override fun isLastPage(): Boolean {
                return githubRepoViewModel.isLastPage()
            }

            override fun loadMore() {
                githubRepoViewModel.pageNumber = githubRepoViewModel.pageNumber + 1
                githubRepoViewModel.getGithubRepoList()
            }

        })

        observe(githubRepoViewModel.githubRepoListViewStateLD, githubRepoListLDObserver)

        observe(liveData = githubRepoViewModel.errorMessageCommunicator, observer = errorObserver)

    }

    private fun setWorkManager() {


    }

    private val githubRepoListLDObserver = Observer<GithubRepoListViewState> { response ->
        binding.progressBar.visibility = View.GONE
        binding.emptyContainer.visibility = View.GONE
        response?.let {
            langRecyclerViewAdapter.submitList(response.language.toMutableList())
            when {
                it.githubList.isNotEmpty() -> githubRepoRecyclerViewAdapter.submitList(it.githubList.toMutableList())
                else -> binding.emptyContainer.visibility = View.VISIBLE
            }

        }
    }
    private val errorObserver = Observer<Event<String>> { event ->
        event.getContentIfNotHandled()?.let {
            errorToast(it)
        }
    }

    override fun onClick(v: View?) {}

}