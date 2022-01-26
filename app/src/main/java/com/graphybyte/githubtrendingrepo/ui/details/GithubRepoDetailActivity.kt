package com.graphybyte.githubtrendingrepo.ui.details

import android.view.View
import com.graphybyte.githubtrendingrepo.R
import com.graphybyte.githubtrendingrepo.core.BaseActivity
import com.graphybyte.githubtrendingrepo.databinding.ActivityGithubRepoDetailBinding
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity
import com.graphybyte.githubtrendingrepo.utils.AppConstants.GITHUB_REPO
import com.graphybyte.githubtrendingrepo.utils.hideStatusBar
import com.graphybyte.githubtrendingrepo.utils.openBrowser
import com.graphybyte.githubtrendingrepo.utils.shareUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubRepoDetailActivity : BaseActivity<ActivityGithubRepoDetailBinding>() {

    private var githubRepoData: GithubEntity? = null

    override fun setActivityView() = R.layout.activity_github_repo_detail

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.share_BTN -> shareUrl(url = githubRepoData?.htmlUrl ?: "")
                R.id.visit_BTN -> openBrowser(url = githubRepoData?.htmlUrl ?: "")
            }
        }
    }


    override fun init() {
        hideStatusBar()
        getBundleData()

    }

    private fun getBundleData() {
        intent.extras?.let {
            githubRepoData = it.getSerializable(GITHUB_REPO) as GithubEntity
        }
        githubRepoData?.let {
            binding.data = it
        }
    }
}