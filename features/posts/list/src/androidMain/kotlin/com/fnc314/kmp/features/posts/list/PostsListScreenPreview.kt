package com.fnc314.kmp.features.posts.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun PostsListScreenPreview() {
    PostsListScreen(
        postsListViewModel = koinViewModel<PostsListViewModel>()
    )
}