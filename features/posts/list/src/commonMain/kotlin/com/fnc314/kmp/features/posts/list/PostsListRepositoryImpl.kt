@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp.features.posts.list

import com.fnc314.kmp.designsystem.widgets.tile.post.PostUIModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

internal class PostsListRepositoryImpl() : PostsListRepository {
    private val postSet: MutableList<PostUIModel> =
        mutableListOf(
            PostUIModel(
                author = "AUTHOR",
                postDate = Clock.System.now(),
                postTitle = "POST-by-Author",
                postBody = "Post BODY 1 ".repeat(Random.Default.nextInt(from = 2, until = 10)),
                reactions = buildMap {
                    put(PostUIModel.PostReaction.Like, Random.Default.nextInt(from = 5, until = 20))
                    put(PostUIModel.PostReaction.Dislike, 5)
                },
                comments = buildSet {
                    add(
                        PostUIModel.PostComment(
                            author = "Comment Author2",
                            commentDate = Clock.System.now().plus(1.days),
                            commentBody = "Comment ".repeat(Random.Default.nextInt(until = 10)),
                            comments = emptySet(),
                        )
                    )
                }
            ),
            PostUIModel(
                author = "AUTHOR2",
                postDate = Clock.System.now(),
                postTitle = "POST-by-AUTHOR2",
                postBody = "Post BODY 2 ".repeat(Random.Default.nextInt(from = 2, until = 10)),
                reactions = buildMap {
                    put(PostUIModel.PostReaction.Like, 5)
                    put(PostUIModel.PostReaction.Dislike, Random.Default.nextInt(from = 5, until = 20))
                },
                comments = buildSet {
                    add(
                        PostUIModel.PostComment(
                            author = "Comment Author",
                            commentDate = Clock.System.now().plus(2.minutes),
                            commentBody = "Comment ".repeat(Random.Default.nextInt(until = 10)),
                            comments = emptySet(),
                        )
                    )
                }
            )
        )

    private val _posts: MutableSharedFlow<List<PostUIModel>> = MutableSharedFlow()
    override suspend fun posts(): Flow<List<PostUIModel>> = flow {
        emit(postSet)
    }
        .flowOn(Dispatchers.IO)

    init {
        _posts.tryEmit(postSet)
            .also {
                Napier.i("TryEmit $it")
            }
    }

    override suspend fun updatePostReaction(postIndex: Int, reaction: PostUIModel.PostReaction) {
        postSet[postIndex].let {
            it.copy(
                reactions = buildMap {
                    it.reactions.onEach { existingReaction ->
                        if (existingReaction.key == reaction) {
                            put(reaction, existingReaction.value + 1)
                        } else {
                            put(existingReaction.key, existingReaction.value)
                        }
                    }
                }
            )
        }.also { postSet[postIndex] = it }

        _posts.tryEmit(postSet)
    }
}