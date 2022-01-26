package com.graphybyte.githubtrendingrepo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.graphybyte.githubtrendingrepo.core.BaseViewModel
import com.graphybyte.githubtrendingrepo.core.Event
import com.graphybyte.githubtrendingrepo.core.State
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity
import com.graphybyte.githubtrendingrepo.utils.AppConstants.languages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) : BaseViewModel<GithubRepoViewState>(initialState = initialStateValue) {

    var pageNumber: Long = 0
    private val repositories = mutableListOf<GithubEntity>()

    companion object {
        val initialStateValue = GithubRepoViewState(
            githubRepoListViewState = GithubRepoListViewState(
                isLoading = false,
                githubList = arrayListOf(),
                language = listOf()
            )

        )
    }

    /*
* It Used showing error msg..
* */
    private val _errorMessagesDeliverer: MutableLiveData<Event<String>> by lazy { MutableLiveData<Event<String>>() }
    val errorMessageCommunicator = _errorMessagesDeliverer as LiveData<Event<String>>

    val githubRepoListViewStateLD = Transformations.map(viewState) { state -> state.githubRepoListViewState }
    private fun updateGithubRepoListViewState(update: (GithubRepoListViewState) -> GithubRepoListViewState) {
        updateViewState { it.copy(githubRepoListViewState = update(it.githubRepoListViewState)) }
    }

    fun getGithubRepoList() {
        viewModelScope.launch {
            githubRepoRepository.getGithubRepo(pageNumber)
                .catch { Timber.d("Error with github repos") }
                .collect { state ->
                    when (state) {
                        is State.Loading -> updateGithubRepoListViewState { it.copy(isLoading = true) }
                        is State.Success -> {
                            repositories.addAll(state.data.items ?: listOf())
                            updateGithubRepoListViewState { it.copy(isLoading = false, githubList = repositories, language = languages) }
                        }
                        is State.ResponseError -> {
                            _errorMessagesDeliverer.postValue(Event(state.message))
                            updateGithubRepoListViewState { it.copy(isLoading = false) }
                        }
                        is State.ExceptionError -> {
                            _errorMessagesDeliverer.postValue(Event(state.errorMessage))
                            updateGithubRepoListViewState { it.copy(isLoading = false) }
                        }
                    }
                }
        }
    }

    fun isLastPage() = repositories.isNotEmpty() && repositories[0].page >= repositories[0].totalPages


}