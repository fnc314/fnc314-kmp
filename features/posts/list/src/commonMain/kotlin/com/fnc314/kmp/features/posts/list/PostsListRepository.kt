@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp.features.posts.list

import com.fnc314.kmp.designsystem.widgets.tile.post.PostUIModel
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

interface PostsListRepository {
    suspend fun posts(): Flow<List<PostUIModel>>

    suspend fun updatePostReaction(postIndex: Int, reaction: PostUIModel.PostReaction)
}