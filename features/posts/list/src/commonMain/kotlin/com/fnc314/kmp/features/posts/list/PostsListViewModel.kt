package com.fnc314.kmp.features.posts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnc314.kmp.designsystem.widgets.tile.post.PostUIModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostsListViewModel(
    private val postsListRepository: PostsListRepository,
) : ViewModel() {
    private val _postsList: MutableStateFlow<List<PostUIModel>> = MutableStateFlow(emptyList())
    val postsFlow: StateFlow<List<PostUIModel>> = _postsList.asStateFlow()

    init {
        viewModelScope.launch {
            postsListRepository
                .posts()
                .collectLatest {
                    Napier.i("Collected $it")
                    _postsList.value = it
                }
        }
    }

    fun onPostClick(
        postIndex: Int,
        reaction: PostUIModel.PostReaction
    ) {
        viewModelScope.launch {
            postsListRepository.updatePostReaction(
                postIndex = postIndex,
                reaction = reaction
            )
        }
    }
}