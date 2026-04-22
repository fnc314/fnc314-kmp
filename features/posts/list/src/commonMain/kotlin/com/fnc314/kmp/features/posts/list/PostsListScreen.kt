package com.fnc314.kmp.features.posts.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fnc314.kmp.designsystem.widgets.tile.post.PostTile
import com.fnc314.kmp.designsystem.widgets.tile.post.PostUIModel

@Composable
fun PostsListScreen(
    modifier: Modifier = Modifier,
    postsListViewModel: PostsListViewModel
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        itemsIndexed(
            items = postsListViewModel.postsFlow.value,
            key = { index: Int, item: PostUIModel -> item.key(index) },
        ) { index: Int, item: PostUIModel ->
            PostTile(
                modifier = modifier,
                post = item
            ) { postReaction ->
                postsListViewModel.onPostClick(
                    postIndex = index,
                    reaction = postReaction
                )
            }
        }
    }
}